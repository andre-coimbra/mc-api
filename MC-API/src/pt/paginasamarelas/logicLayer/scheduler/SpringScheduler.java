package pt.paginasamarelas.logicLayer.scheduler;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pt.paginasamarelas.dataLayer.entities.Advertiser;
import pt.paginasamarelas.dataLayer.entities.AdvertiserID;
import pt.paginasamarelas.dataLayer.entities.Campaign;
import pt.paginasamarelas.dataLayer.entities.Diagnostic;
import pt.paginasamarelas.dataLayer.entities.Operations;
import pt.paginasamarelas.dataLayer.entities.Response;
import pt.paginasamarelas.dataLayer.hibernate.HibernateOraUtil;
import pt.paginasamarelas.dataLayer.hibernate.HibernateUtil;
import pt.paginasamarelas.dataLayer.hibernate.QueryCampaignDB;
import pt.paginasamarelas.logicLayer.common.log4j.AppendLog4jLogger;
import pt.paginasamarelas.logicLayer.common.log4j.LogKeys;
import pt.paginasamarelas.logicLayer.controller.*;
import pt.paginasamarelas.logicLayer.operations.AdvertiserCreator;
import pt.paginasamarelas.logicLayer.operations.CampaignUpdater;
import pt.paginasamarelas.logicLayer.operations.JacksonConverter;

public final class SpringScheduler 
{
	public static org.apache.log4j.Logger logger;
	  
	QueryCampaignDB query = new QueryCampaignDB();
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Session session = sessionFactory.openSession();
	private ApplicationContext context;
	
		
	public void decider() throws Throwable
	{
		context = new ClassPathXmlApplicationContext("beans.xml");
		boolean TESTE_NO_REAL=false;
		boolean campanhaGAespecial = false;
		boolean swNormalRun = false; /* if false=specialRun (only for one or two campaigns...) */
		Random rnum = new Random();
		int retUpd = 0;
		int W_MAX_UPDATES = 500;
		boolean sw04abortProcess = false;
		boolean sw05includeStdAdcopies = false;
		
	      Properties logProperties;
	      logProperties = new Properties();
	    try {
	    	FileInputStream is = null;
	    	if (swNormalRun) {
	    		is = new FileInputStream("\\\\ciclope\\dossr_desenv\\MC_API\\Exe\\Resources\\log4j.properties");
	    	} else {
	    		is = new FileInputStream(".\\Resources\\log4j.properties");
	    	}
	      logProperties.load(is);
	      PropertyConfigurator.configure(logProperties);
	      logger = Logger.getLogger("ERRORREPORT_APPENDER");
//	      logger = Logger.getLogger(this.getClass());
	  	  String lastIntfDate = null;
	  	  // Data do Processamento no formato: 2017-01-26 14:00:40 
	  	  String procDate = null;
	  	  DateFormat myFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:SS");
	  	  procDate = myFormat.format(new Date());

/* FM 20.09.2016 - Esquema para evitar que haja duas instâncias a correr ao mesmo tempo!
 * Aguarda uns segs para não começarem as duas threads ao mesmo tempo !
*/	  	  // Pause for numSecs seconds
	  	  int pauseTime = rnum.nextInt(30) * 1000;
	  	  System.out.println("[Spring_SCH] Data_processamento:" + procDate + "   Vai esperar " + pauseTime + " ms\n");
	  	  logger.info("[Spring_SCH] Vai esperar " + pauseTime + " ms\n");
	  	  Thread.sleep( pauseTime );
	  	// FM 21.09.2016
			if (swNormalRun) {
				Session dossrSession = HibernateOraUtil.openOraSession();
				retUpd = query.updDossrPrmIntfDateRV(dossrSession);
				if (retUpd>0) {
					System.out.println("[Spring_SCH] Erro "+ retUpd +" ao marcar RowVersion data de fim do processamento\n");
					logger.info("[Spring_SCH] Erro "+ retUpd +" ao marcar RowVersion data de fim do processamento\n");
				}
			}

	  	  
	  	  if (!(session.isOpen()))
				session = sessionFactory.openSession();
			List<?> newCampaignsList = query.getNewAdvertisersHQL(session,swNormalRun);
			ReadCampaignController readCampaignController = (ReadCampaignController) context.getBean("readcampaigncontroller");
			CreateCampaignController createCampaignController = (CreateCampaignController) context.getBean("createcampaigncontroller");
			DeleteCampaignController deleteCampaignController = (DeleteCampaignController) context.getBean("deletecampaigncontroller");
			JacksonConverter jacksonConverter = (JacksonConverter) context.getBean("jacksonConverter");
			Response responseobj = (Response) context.getBean("response");
			Advertiser retAdv = null;
			Advertiser cmcAdv = null;
			String response = null;
			String diagnosticCode = null;
			String newStatus = null;
			int regLidos = 0;
			int campsIguais = 0;
			int campsInserted = 0;
			int campsUpdated = 0;
			int campsCancelled = 0;
			int campsRejected = 0;
			int swOpSuccess = 0;
			String strOpSuccess = null;
			boolean sw02OpSuccess = false;
			boolean sw03CheckMCCampaignStatus = false;
			Campaign cmcCampaign = null;
			AdvertiserCreator advCreator = null;
			CampaignUpdater campUpdater = new CampaignUpdater();
			ObjectMapper objectMapper = new ObjectMapper();
			Logger apLogger = Logger.getLogger(SpringScheduler.class.getName());
			Hashtable<String, String> campaignRefMap = new Hashtable<String, String>();
			if (!(newCampaignsList==null) && newCampaignsList.size()>0) {
				lastIntfDate = query.getLastIntfDate();
				if (lastIntfDate.substring(11, 13).equals("20") || lastIntfDate.substring(11, 15).equals("19:5")) {
					sw03CheckMCCampaignStatus = true;
// FM 10.01.2017
					W_MAX_UPDATES = 250;
				}
				Iterator<?> newCampaignsIterator = newCampaignsList.listIterator();
				
				
				objectMapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		//Esta configuração evita que os dados a null sejam inseridos no JSON final
				objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
				objectMapper.setSerializationInclusion(Inclusion.NON_EMPTY);
				objectMapper.setSerializationInclusion(Inclusion.NON_DEFAULT);			

//  !!! ---------------------------------------------\/
				while(newCampaignsIterator.hasNext() && regLidos<W_MAX_UPDATES && !(sw04abortProcess)) 
			    {
					regLidos++;
					try {
						Object[] newCampaign = (Object[]) newCampaignsIterator.next();
						String status = newCampaign[1].toString();
						String external_id = newCampaign[0].toString();
						String subscrId = newCampaign[2].toString();
						String manMngdInd = null;
						String newStatusMsg = null;
						String origem = null;
// FM 07.02.2017
						int campaignId = Integer.parseInt(external_id.substring(external_id.lastIndexOf('_') + 1));
						if (campaignId>188490) {
							sw05includeStdAdcopies = false;
						}
						else {
							sw05includeStdAdcopies = true;							
						}
// Criar Hashtable com os registos que vão sendo tratados para não tratar a mesma campanha duas vezes !
						if (campaignRefMap.isEmpty() || (!(campaignRefMap.isEmpty()) && !(campaignRefMap.containsKey(external_id)))) {
							campaignRefMap.put(external_id, ""+regLidos) ;
							if (!(newCampaign[4]==null))
								manMngdInd = newCampaign[4].toString();
							if (!(newCampaign[5]==null))
								origem = newCampaign[5].toString();
							newStatus = null;
// FM 15.09.2016 - Campanhas especiais são aquelas que têm mais que uma fig GAWH para o mesmo CampaignId/Título
							if (external_id.equals("92925896_3359317_180956"))
            					campanhaGAespecial = true;
							if (origem==null) {
								System.out.println("**** [SpringSch] " + external_id + "  sts:" + status + " manMngdInd:" + manMngdInd + "\n");
								logger.info("**** [SpringSch] " + external_id + "  sts:" + status + " manMngdInd:" + manMngdInd + "\n");
							}
							else {
								System.out.println("**** [SpringSch] " + external_id + "  sts:" + status + " manMngdInd:" + manMngdInd + " origem:" + origem + "\n");
								logger.info("**** [SpringSch] " + external_id + "  sts:" + status + " manMngdInd:" + manMngdInd + " origem:" + origem + "\n");
							}

							if (status.equals("4 - Sent to BMS") && sw03CheckMCCampaignStatus) {
		            			response = readCampaignController.getMatchcraftCampaign(external_id);
		            			jacksonConverter.refactoredResponse(response);
		            			responseobj = jacksonConverter.responseToJSON(response);
		            			Diagnostic[] auxDiagsR = responseobj.getDiagnostics();
		            			if (!(auxDiagsR==null)) {
		            				System.out.println("**!! [SpringSch] " + external_id + " deu erro na Leitura - code:" + auxDiagsR[0].getCode() + " msg:" + auxDiagsR[0].getMessage());
		            				logger.info("**!! [SpringSch] " + external_id + " deu erro na Leitura - code:" + auxDiagsR[0].getCode() + " msg:" + auxDiagsR[0].getMessage());
			            			if (auxDiagsR[0].getCode().equals("exceededDailyLimit")) {
			            				sw04abortProcess = true;
			            			}
		        				}
		            			else {
		            				Operations[] auxOps = responseobj.getOperations();
			            			/* if camp exists faz update else cria campanha */
			            			Diagnostic[] auxDiags = auxOps[0].getDiagnostics();
			            			if (auxDiags==null) {
			            				/* É porque leu sem erros ! */
			            				diagnosticCode = "campaignReadSuccess";
			            			}
			            			else {
				            			diagnosticCode = auxDiags[0].getCode();
			            			}
			            			if (diagnosticCode.equals("exceededDailyLimit")) {
			            				sw04abortProcess = true;
			            			}
			            			else {
				            			if (diagnosticCode.equals("campaignReadSuccess")) {
					            				retAdv = auxOps[0].getResult();
					            				if (manMngdInd==null || !(manMngdInd.substring(0,1).equals("1"))) {
// Campanha passa a estado Running no CMC:
					            					if (retAdv.getUserStatus().equals("active")) {
									        			newStatus = "5a - Running";
									        			newStatusMsg = null;
								            			if (!(session.isOpen()))
								            				session = sessionFactory.openSession();
								            			sw02OpSuccess = query.setCampaignStatus(session,external_id,newStatus, newStatusMsg);
								            			if (!sw02OpSuccess)
								            				System.out.println("[campRun] Erro ao actualizar estado da Campanha "+ external_id +" para "+ newStatus +"\n");
								            			else {
									            			campsUpdated++;
								            			}
					            					}
					            				}
				            			}
			            			}
		            			}
							}
							else {
								switch (status) 
								{
						            case "3 - Ready to send to BMS": 
						            case "4 - Sent to BMS":
						            case "5a - Running":  
					            			response = readCampaignController.getMatchcraftCampaign(external_id);
					            			jacksonConverter.refactoredResponse(response);
					            			responseobj = jacksonConverter.responseToJSON(response);
					            			Diagnostic[] auxDiagsR = responseobj.getDiagnostics();
					            			if (!(auxDiagsR==null)) {
					            				System.out.println("**!! [SpringSch] " + external_id + " deu erro na Leitura - code:" + auxDiagsR[0].getCode() + " msg:" + auxDiagsR[0].getMessage());
					            				logger.info("**!! [SpringSch] " + external_id + " deu erro na Leitura - code:" + auxDiagsR[0].getCode() + " msg:" + auxDiagsR[0].getMessage());
				            				}
					            			else {
					            				Operations[] auxOps = responseobj.getOperations();
						            			/* if camp exists faz update else cria campanha */
						            			Diagnostic[] auxDiags = auxOps[0].getDiagnostics();
						            			if (auxDiags==null) {
						            				/* É porque leu sem erros ! */
						            				diagnosticCode = "campaignReadSuccess";
						            			}
						            			else {
							            			Diagnostic diagnostic = new Diagnostic();
			//		            			diagnostic = objectMapper.readValue(response, Diagnostic.class);
							            			diagnosticCode = auxDiags[0].getCode();
						            			}
						            			cmcCampaign = new Campaign();
						            			advCreator = new AdvertiserCreator();
						            			cmcAdv = advCreator.createAdvertiser(external_id, campanhaGAespecial, sw05includeStdAdcopies);
						            			cmcCampaign.setAdvertiser(cmcAdv);
						            			if (diagnosticCode.equals("noSuchAdvertiser")) {
						            				if (!(manMngdInd==null) && manMngdInd.substring(0,1).equals("1")) {
							            				System.out.println("Campanha "+ external_id +" manually-Managed não existe na Matchcraft !\n");
							            				logger.info("Campanha "+ external_id +" manually-Managed não existe na Matchcraft !\n");
								            			if (!(session.isOpen()))
								            				session = sessionFactory.openSession();
								            			sw02OpSuccess = query.setInterfaceDate(session,external_id);
								            			if (!sw02OpSuccess)
								            				System.out.println("[campUpd] Erro ao actualizar interfaceDate - id:"+ external_id +" \n");
						            				}
						            				else {
									        			response = createCampaignController.createMatchcraftCampaign(cmcCampaign);
								            			jacksonConverter.refactoredResponse(response);
								            			responseobj = jacksonConverter.responseToJSON(response);
								            			if (responseobj==null) {
								            				System.out.println("[campIns] Erro java na Campanha "+ external_id +" responseObj a null\n");
								            				logger.info("[campIns] Erro java na Campanha "+ external_id +" responseObj a null\n");
								            			}
								            			else {
									            			Operations[] auxOpsI = responseobj.getOperations();
									            			/* if camp exists faz update else cria campanha */
									            			Diagnostic[] auxDiagsI = auxOpsI[0].getDiagnostics();
									            			if (auxDiagsI==null) {
									            				sw02OpSuccess = true;
									            			}
									            			else {
										            			diagnosticCode = auxDiagsI[0].getCode();
										            			sw02OpSuccess = false;	            				
									            			}
									            			if (!sw02OpSuccess) {
									            				System.out.println("[campIns] Erro ao criar Campanha "+ external_id +" na Matchcraft (err-code:" +diagnosticCode+ ")\n");
									            				logger.info("[campIns] Erro ao criar Campanha "+ external_id +" na Matchcraft (err-code:" +diagnosticCode+ ")\n");
									            				if (diagnosticCode.equals("invalidValue")) {
										            				campsRejected++;
												        			newStatus = "99 - Error";
												        			newStatusMsg = auxDiagsI[0].getMessage().replaceAll("'", " ");
											            			if (!(session.isOpen()))
											            				session = sessionFactory.openSession();
											            			sw02OpSuccess = query.setCampaignStatus(session,external_id,newStatus, newStatusMsg);
											            			if (!sw02OpSuccess)
											            				System.out.println("[campIns] Erro ao actualizar estado da Campanha "+ external_id +" para "+ newStatus +"\n");
									            				}
									            			}	
									            			else {
											        			campsInserted++;
											        			if ( (!(TESTE_NO_REAL)) && status.equals("3 - Ready to send to BMS")) {
												        			newStatus = "4 - Sent to BMS";
											            			if (!(session.isOpen()))
											            				session = sessionFactory.openSession();
											            			sw02OpSuccess = query.setCampaignStatus(session,external_id,newStatus, newStatusMsg);
											            			if (!sw02OpSuccess)
											            				System.out.println("[campUpd] Erro ao actualizar estado da Campanha "+ external_id +" para "+ newStatus +"\n");
											        			}
									            				System.out.println("[campIns] Campanha "+ external_id +" criada na Matchcraft com sucesso.\n");
									            				logger.info("[campIns] Campanha "+ external_id +" criada na Matchcraft com sucesso.\n");
									            			}
								            			}
						            				}
							            		}
						            			else {
							            			if (diagnosticCode.equals("campaignReadSuccess")) {

							            				//			            			retAdv = objectMapper.readValue(response, Advertiser.class);
							            				retAdv = auxOps[0].getResult();
							            				if (!(manMngdInd==null) && manMngdInd.substring(0,1).equals("1")) {
					// Campanha está manually managed é para inactivar na Matchcraft (se estiver activa)
							            					if (!(retAdv.getUserStatus().equals("deleted"))) {
							    				            	//deleteCampaignController
							    			        			response = deleteCampaignController.deleteMatchcraftCampaign(external_id);
							    		            			retAdv = objectMapper.readValue(response, Advertiser.class);
								            					if (retAdv==null) {
					    				            				System.out.println("Campanha "+ external_id +" cancelada com sucesso\n");
					    				            				campsCancelled++;    			            						
								            					}
								            					else {
								    		            			AdvertiserID retAdvId = retAdv.getAdvertiserId();
								    		            			if (!(retAdvId == null)) {
								    			            			if (retAdv.getAdvertiserId().getRipId() > 0) {
								    				            				System.out.println("Campanha "+ external_id +" cancelada com sucesso\n");
								    				            				campsCancelled++;
								    			            			}
								    			            			else {
								    			            				String retExtId=retAdv.getAdvertiserId().getExternalId();
								    			            				if (!(retExtId==null)) {
								    				            				System.out.println("Campanha "+ retExtId +" cancelada com sucesso\n");
								    				            				campsCancelled++;		            					
								    			            				}
								    			            				else {
									    				            				System.out.println("[DEL_CAMP] Campanha "+ external_id +" not found !?\n");            						    			            						
								    			            				}
								    			            			}
							    			            			}
							    		            			}
							            					}
							            				}
							            				else {
// FM 26.01.2017 - Se a campanha está paused na Matchcraft não trata !
							            					if (retAdv.getUserStatus().equals("paused")) {
				    				            				System.out.println("[UPD_CAMP] Campanha "+ external_id +" está PAUSED na Matchcraft!\n");
							            					}
							            					else {
								            					Campaign mcCampaign = new Campaign();
								            					mcCampaign.setAdvertiser(retAdv);
						
										            			strOpSuccess = campUpdater.campaignUpdater(subscrId, status, mcCampaign, cmcCampaign, campanhaGAespecial, sw05includeStdAdcopies);
										            			if (strOpSuccess.equals("1")) {
											            			campsUpdated++;
												        			if ( !(TESTE_NO_REAL) ) {
												        				if (status.equals("3 - Ready to send to BMS")) {
												        					newStatus = "4 - Sent to BMS";
												        				}
												        				else {
												        					newStatus = status;
												        				}
												            			if (!(session.isOpen()))
												            				session = sessionFactory.openSession();
												            			sw02OpSuccess = query.setCampaignStatus(session,external_id,newStatus, newStatusMsg);
												            			if (!sw02OpSuccess)
												            				System.out.println("[campUpd] Erro ao actualizar estado da Campanha "+ external_id +" para "+ newStatus +"\n");
												        			}
										            			}
										            			else {
											            			if (strOpSuccess.equals("2")) {
											            				System.out.println("Campanha "+ external_id +" sem alterações\n");
											            				campsIguais++;
											            			}
										            				else {
											            				System.out.println("Erro ao actualizar a Campanha "+ external_id +"\n");		            				
					/* se fôr um determinado erro tem que pôr no estado 99! */
											            				campsRejected++;
											            				newStatus = "99 - Error";
											            				if (!(strOpSuccess==null)) {
											            					if (strOpSuccess.length()>1 && strOpSuccess.charAt(1)==':')
												            					newStatusMsg = strOpSuccess.substring(2).replaceAll("'", " ");
											            					else
												            					newStatusMsg = newStatus.replaceAll("'", " ");
											            				}
												            			if (!(session.isOpen()))
												            				session = sessionFactory.openSession();
												            			sw02OpSuccess = query.setCampaignStatus(session,external_id,newStatus, newStatusMsg);
												            			if (!sw02OpSuccess)
												            				System.out.println("[campUpd] Erro ao actualizar estado da Campanha "+ external_id +" para "+ newStatus +"\n");
										            				}
										            			}
							            					}
							            				}
						            				}
							            			else {
							            				System.out.println("[campIns] Erro " + diagnosticCode + " na leitura da Campanha "+ external_id +" \n");
							            				logger.info("[campIns] Erro " + diagnosticCode + " na leitura da Campanha "+ external_id +" \n");
							            			}
						            			}
					            			}
					            			break;
					                     	
						            case "6 - Cancelled":  
		// FM 18.05.2016 - Campanha pode ainda não existir na Matchcraft !
						            	response = readCampaignController.getMatchcraftCampaign(external_id);
				            			jacksonConverter.refactoredResponse(response);
				            			responseobj = jacksonConverter.responseToJSON(response);
				            			Diagnostic[] auxDiagsR6 = responseobj.getDiagnostics();
				            			if (!(auxDiagsR6==null)) {
				            				System.out.println("**!! [SpringSch] " + external_id + " deu erro na Leitura - code:" + auxDiagsR6[0].getCode() + " msg:" + auxDiagsR6[0].getMessage());
				            				logger.info("**!! [SpringSch] " + external_id + " deu erro na Leitura - code:" + auxDiagsR6[0].getCode() + " msg:" + auxDiagsR6[0].getMessage());
				            			}
				            			else {
					            			Operations[] auxOps6 = responseobj.getOperations();
					            			/* if camp exists faz update else cria campanha */
					            			Diagnostic[] auxDiags6 = auxOps6[0].getDiagnostics();
					            			if (auxDiags6==null) {
					            				/* É porque leu sem erros ! */
					            				diagnosticCode = "campaignReadSuccess";
					            			}
					            			else {
						            			diagnosticCode = auxDiags6[0].getCode();
					            			}
					            			cmcCampaign = new Campaign();
					            			advCreator = new AdvertiserCreator();
					            			cmcAdv = advCreator.createAdvertiser(external_id, campanhaGAespecial, sw05includeStdAdcopies);
					            			cmcCampaign.setAdvertiser(cmcAdv);
					            			if (diagnosticCode.equals("noSuchAdvertiser")) {
					            				System.out.println("Campanha "+ external_id +" inactiva não existe na Matchcraft !\n");
					            				AppendLog4jLogger.info(LogKeys.errorReportLogKey,"Campanha "+ external_id +" inactiva não existe na Matchcraft !\n");
						            			if (!(session.isOpen()))
						            				session = sessionFactory.openSession();
					            				sw02OpSuccess = query.setInterfaceDate(session,external_id);
						            			if (!sw02OpSuccess)
						            				System.out.println("[Spring_SCH - InactCamp] Erro ao actualizar interfaceDate - id:"+ external_id +" \n");
					            			}
					            			else {
								            	//deleteCampaignController
							        			response = deleteCampaignController.deleteMatchcraftCampaign(external_id);
					            				logger.info("[DEL_CAMP] Campanha "+ external_id +" cancelada com sucesso\n");
						            			jacksonConverter.refactoredResponse(response);
						            			responseobj = jacksonConverter.responseToJSON(response);
						            			retAdv = objectMapper.readValue(response, Advertiser.class);
						            			AdvertiserID retAdvId = retAdv.getAdvertiserId();
						            			if (!(retAdvId == null)) {
							            			if (retAdv.getAdvertiserId().getRipId() > 0) {
								            				System.out.println("Campanha "+ external_id +" cancelada com sucesso\n");
								            				logger.info("Campanha "+ external_id +" cancelada com sucesso\n");
								            				campsCancelled++;
									            			sw02OpSuccess = query.setInterfaceDate(session,external_id);
									            			if (!sw02OpSuccess)
									            				System.out.println("[DEL_CAMP] Erro ao actualizar interfaceDate - id:"+ external_id +" \n");
							            			}
							            			else {
							            				String retExtId=retAdv.getAdvertiserId().getExternalId();
							            				if (!(retExtId==null)) {
								            				System.out.println("Campanha "+ retExtId +" cancelada com sucesso\n");
								            				logger.info("Campanha "+ external_id +" cancelada com sucesso\n");
								            				campsCancelled++;
									            			sw02OpSuccess = query.setInterfaceDate(session,external_id);
									            			if (!sw02OpSuccess)
									            				System.out.println("[DEL_CAMP] Erro ao actualizar interfaceDate - id:"+ external_id +" \n");
							            				}
							            				else {
								            				System.out.println("[DEL_CAMP] Campanha "+ external_id +" not found !?\n"); 
								            				logger.info("[DEL_CAMP] Campanha "+ external_id +" not found !?\n"); 
							            				}
							            			}
						            			}
						            			else {
						            				campsCancelled++;
		 				            			}
					            			}
				            			}
				            			break;
						            case "7a - Completed by date":  
							    				System.out.println("Campanha "+ external_id +" esta no estado \"7a - Completed by date\"\n");            				
						            	
						                     	break;
						           
						            default: 
						                     	break;
								}
							}
						}
						else {
							System.out.println("[Spring_SCH] " + external_id + " registo já foi trabalhado \n");
							logger.info("[Spring_SCH] " + external_id + " registo já foi trabalhado \n");
						}
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
		    }
			else {
			System.out.println("[Spring_SCH] Cursor Main não devolveu registos ! \n");
			logger.info("[Spring_SCH] Cursor Main não devolveu registos ! \n");
			}
			System.out.println("[Spring_SCH] ------- FIM loop newCampaignsIterator -------");
			logger.info("[Spring_SCH] ------- FIM loop newCampaignsIterator -------");
// FM 23.06.2016
// FM 09.01.2017 - Se chegou ao limite de #regs, não altera a data de controle para poder trabalhar todos os registos			if (swNormalRun) {
			if (swNormalRun && (sw03CheckMCCampaignStatus || ((!sw03CheckMCCampaignStatus) && regLidos<W_MAX_UPDATES))
							|| ((!sw03CheckMCCampaignStatus) && (procDate.substring(11, 13).equals("20") || procDate.substring(11, 15).equals("19:5")))) {				
				Session dossrSession = HibernateOraUtil.openOraSession();
				retUpd = query.updDossrIntfDate(dossrSession, swNormalRun);
				if (retUpd>0) {
					System.out.println("[Spring_SCH] Erro "+ retUpd +" ao marcar data de fim do processamento\n");
					logger.info("[Spring_SCH] Erro "+ retUpd +" ao marcar data de fim do processamento\n");
				}
			}
			System.out.println("[Spring_SCH] Campanhas lidas "+ regLidos +" \n");
			System.out.println("[Spring_SCH] Campanhas criadas "+ campsInserted +" \n");
			System.out.println("[Spring_SCH] Campanhas alteradas "+ campsUpdated +" \n");		
			System.out.println("[Spring_SCH] Campanhas inactivadas "+ campsCancelled +" \n");		
			System.out.println("[Spring_SCH] Campanhas não processadas "+ campsRejected +" \n");
			System.out.println("[Spring_SCH] Campanhas sem alteração "+ campsIguais);
/* FM 08.06.2016 */
			logger.info("[LSpring_SCH] Campanhas lidas "+ regLidos);
			logger.info("[LSpring_SCH] Campanhas criadas "+ campsInserted);
//		AppendLog4jLogger.info(LogKeys.errorReportLogKey,"[LSpring_SCH] Campanhas lidas "+ regLidos);
//		AppendLog4jLogger.info(LogKeys.errorReportLogKey,"[LSpring_SCH] Campanhas criadas "+ campsInserted);
//		apLogger.info("[LSpring_SCH] Campanhas lidas "+ regLidos);
			AppendLog4jLogger.info(LogKeys.errorReportLogKey,"[LSpring_SCH] Campanhas alteradas "+ campsUpdated);
			AppendLog4jLogger.info(LogKeys.errorReportLogKey,"[LSpring_SCH] Campanhas inactivadas "+ campsCancelled);
			AppendLog4jLogger.info(LogKeys.errorReportLogKey,"[LSpring_SCH] Campanhas não processadas "+ campsRejected);
			AppendLog4jLogger.info(LogKeys.errorReportLogKey,"[LSpring_SCH] Campanhas sem alteração "+ campsIguais);
			this.finalize();
/*	*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("[LSpring_SCH] Excepcao: "+ e.getMessage() );
		}
	}
		
	public static void main(String[] args) {
		try {
			SpringScheduler mySch = new SpringScheduler();
			mySch.decider();
	    } catch (Exception ex) {
	    	System.out.println(ex);
	    	ex.printStackTrace(System.out);
	    	System.exit(1);
	    } catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

}
