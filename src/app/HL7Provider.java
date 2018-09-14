package app;

import java.awt.List;
import java.util.ArrayList;

import org.apache.derby.tools.sysinfo;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.utilities.graphql.Parser;

import com.google.gson.Gson;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.AddressDt;
import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Bundle.Entry;
import ca.uhn.fhir.model.dstu2.resource.Medication;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.BundleTypeEnum;
import ca.uhn.fhir.model.dstu2.valueset.HTTPVerbEnum;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.parser.BaseParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;

public class HL7Provider {
	
	
	
	public static void main(String[] args) {
	
		System.out.println("Starting...");
	
		
		insertPatient(createSimplePacient("Ronaldo","Fenômeno"));
//		System.out.println(searchPatient("Ronaldo",""));
		
	}
	
	
	public static ArrayList<String[]> searchPatient(String name, String surname, String id) {
		
		ArrayList<String[]> result = new ArrayList<String[]>();
		
		
		// We're connecting to a DSTU1 compliant server in this example
		FhirContext ctx = FhirContext.forDstu2();
		String serverBase = "http://fhirtest.uhn.ca/baseDstu2";
		 
		IGenericClient client = ctx.newRestfulGenericClient(serverBase);
		 
		//Faz busca
		Bundle results;
		if(!id.equals("")) {
			results = client.search().byUrl("Patient?_id="+id).returnBundle(Bundle.class).execute();
			
		}else {
				results = client.search()
			      .forResource(Patient.class)
			      .where(Patient.GIVEN.matches().value(name)).and(Patient.FAMILY.matches().value(surname))
			      .returnBundle(Bundle.class)
			      .execute();
				
		}
				
		int i = 0;
		for (Entry entry : results.getEntry()) {
			
			String[] row = new String[3];
			
			String jsonEncoded = ctx.newJsonParser().encodeResourceToString(entry.getResource());			
			Patient patient = FhirContext.forDstu2().newJsonParser().parseResource(Patient.class, jsonEncoded);
			
			System.out.println(patient.getName().toString());
			System.out.println(patient.getGender());
			System.out.println(patient.getAddress());
			
			String formatedName = patient.getNameFirstRep().getGivenAsSingleString()+" "+patient.getNameFirstRep().getFamilyAsSingleString();
			
			String formatedAdderess = 
					patient.getAddressFirstRep().getCountry()+ ", "+
					patient.getAddressFirstRep().getCity()+ " "+
					"("+patient.getAddressFirstRep().getPostalCode()+")";

			String formatedID = patient.getId().getValue().toString().substring(8,13);	
			
			
			row[0] = formatedID;
			row[1] = formatedName;
			row[2] = formatedAdderess;
			result.add(row);
		i++;
		}
		
		return result;
		
	}
	
	public static String getPacient(String id, String encode) {
		
		String result = "";
		
				FhirContext ctx = FhirContext.forDstu2();
				String serverBase = "http://fhirtest.uhn.ca/baseDstu2";
				IGenericClient client = ctx.newRestfulGenericClient(serverBase);
				 
				//Faz busca pelos parâmetros fornecidos
				Bundle results;
				results = client.search().byUrl("Patient?_id="+id).returnBundle(Bundle.class).execute();
					
				for (Entry entry : results.getEntry()) {
					
					
					if(encode == "json")
						result = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(entry.getResource());
					
					if(encode == "xml")
						result = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(entry.getResource());

				}
				
			
				return result;
	}
	
	public static Patient createPacient(String nome, String sobrenome, String cep, String cidade, String pais) {
		
		//Cria endereço
		AddressDt endereco = new AddressDt();
			endereco.setPostalCode(cep);
			endereco.setCity(cidade);
			endereco.setCountry(pais);
			
		ArrayList<AddressDt> listaEnderecos = new ArrayList<AddressDt>();
		listaEnderecos.add(endereco);
		
		//Cria paciente
		Patient patient = new Patient();
		patient.setId(IdDt.newRandomUuid());
		patient.addName()
			.addFamily(sobrenome)
			.addGiven(nome);
		patient.setAddress(listaEnderecos);
		
		
		Parser p = new Parser();
		
		
		return patient;
	}
	
	public static String[] insertPatient(Patient patient) {
				
		//Cria parser
		
		
		// Cria novo Bundle de entrada 
		Bundle bundle = new Bundle();
		bundle.setType(BundleTypeEnum.TRANSACTION);

		//Adiciona o paciente ao bundle de entrada
		bundle.addEntry()
		   .setFullUrl(patient.getId().getValue())
		   .setResource(patient)
		   .getRequest()
		      .setUrl("Patient")
		      .setMethod(HTTPVerbEnum.POST);
		
		// Cria contexto do banco
		FhirContext ctx = FhirContext.forDstu2();
//		System.out.println(ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(bundle));
		 
		// Cria cliente do banco com contexto do banco
		IGenericClient client = ctx.newRestfulGenericClient("http://fhirtest.uhn.ca/baseDstu2");
		Bundle resp = client.transaction().withBundle(bundle).execute();
		 
		// loga e retorna o resultado da inserção
		System.out.println("Criado paciente com id "+resp.getEntryFirstRep().getResponse().getLocation().substring(8,13));
		System.out.println(ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(resp));
		System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(resp));
		
		
		String[] retornos = new String[3];
		retornos[0] = resp.getEntryFirstRep().getResponse().getLocation().substring(8,13);
		retornos[1] = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(resp);
		retornos[2] = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(resp);
		
		return retornos;
	}
	
	public static Patient createSimplePacient(String nome, String sobrenome) {
		
			
		
		//Cria paciente
		Patient patient = new Patient();
		patient.setId(IdDt.newRandomUuid());
		patient.addName()
			.addFamily(sobrenome)
			.addGiven(nome);
		return patient;
	}
	
}
