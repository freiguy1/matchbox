package org.broadinstitute.macarthurlab.matchbox.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="patient")
public class Patient{
	
	/**
	 * Should this ID be the ID of the patient (which enforces uniqueness)
	 * of allow mongo to generate a object ID (side effect: can insert same patient
	 * many times)
	 * REQUIRED
	 */
	@Id
	private final String id;
	
	/**
	 * OPTIONAL
	 */
	private final String label;
	
	/**
	 * This contains details on who submitted the patient in,
	 * "name", "institution", "href"
	 * REQUIRED
	 */
	private final Map<String,String> contact;
	
	/**
	 * This contains a NCBI taxon identifier
	 * OPTIONAL
	 */
	private final String species;
	
	/**
	 * Allowed values:
	 * "FEMALE"|"MALE"|"OTHER"|"MIXED_SAMPLE"|"NOT_APPLICABLE"
	 * OPTIONAL
	 */
	private final String sex;
	
	/**
	 * This should contain HPO code
	 * OPTIONAL
	 */
	private final String ageOfOnset;
	
	/**
	 * This should contain HPO code
	 * OPTIONAL
	 */
	private final String inheritanceMode;
	
	/**
	 * This contains a list of Maps. each Map is a disorder 
	 * Map looks like,
	 *  "id" : "MIM:######"|"Orphanet:#####"|…
	 *  OPTIONAL
	 */
	private final List<Map<String,String>> disorders;
	
	/**
	 * Features is a list of Feature objects,
	 * *******************************
	 * IF genomicFeatures NOT give:  *
	 * 	REQUIRED  					 *
	 * ELSE:	 					 *
	 *  OPTIONAL  					 *
	 * *******************************
	 */
	private final List<PhenotypeFeature> features;
	
	/**
	 * Represents genomic features and are a list of GenomicFeature objects
	 * *******************************
	 * IF features NOT give:		 *
	 * 	REQUIRED  --------			 *
	 * ELSE:      --------			 *
	 *  OPTIONAL  --------			 *
	 * *******************************
	 */
	private final List<GenomicFeature> genomicFeatures;
	
	
	/**
	 * Default constructor builds empty object
	 */
	public Patient() {
		this.id = "";
		this.label = "";
		this.contact = new HashMap<String, String>();
		this.species = "";
		this.sex = "";
		this.ageOfOnset = "";
		this.inheritanceMode = "";
		this.disorders = new ArrayList<Map<String,String>>();
		this.features = new ArrayList<PhenotypeFeature>();
		this.genomicFeatures = new ArrayList<GenomicFeature>();
	}
	
	
	
	/**
	 * @param id	Id of patient
	 * @param label	label for patient
	 * @param contact	primary contact regarding patient
	 * @param species	species of patient
	 * @param sex	sex of patient
	 * @param ageOfOnset	age of onset of disease
	 * @param inheritanceMode	inheritance mode
	 * @param disorders	disorders affecting patient
	 * @param features	features
	 * @param genomicFeatures	genomic features
	 */
	public Patient(String id, 
					String label, 
					Map<String, String> contact, 
					String species, 
					String sex,
					String ageOfOnset, 
					String inheritanceMode, 
					List<Map<String, String>> disorders, 
					List<PhenotypeFeature> features,
					List<GenomicFeature> genomicFeatures) {
		this.id = id;
		this.label = label;
		this.contact = contact;
		this.species = species;
		this.sex = sex;
		this.ageOfOnset = ageOfOnset;
		this.inheritanceMode = inheritanceMode;
		this.disorders = disorders;
		this.features = features;
		this.genomicFeatures = genomicFeatures;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @return the contact
	 */
	public Map<String, String> getContact() {
		return contact;
	}
	/**
	 * @return the species
	 */
	public String getSpecies() {
		return species;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @return the ageOfOnset
	 */
	public String getAgeOfOnset() {
		return ageOfOnset;
	}
	/**
	 * @return the inheritanceMode
	 */
	public String getInheritanceMode() {
		return inheritanceMode;
	}
	/**
	 * @return the disorders
	 */
	public List<Map<String, String>> getDisorders() {
		return disorders;
	}
	/**
	 * @return the features
	 */
	public List<PhenotypeFeature> getFeatures() {
		return features;
	}
	/**
	 * @return the genomicFeatures
	 */
	public List<GenomicFeature> getGenomicFeatures() {
		return genomicFeatures;
	}


	/* 
	 * To String method
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Patient [id=" + id + ", label=" + label + ", contact=" + contact + ", species=" + species + ", sex="
				+ sex + ", ageOfOnset=" + ageOfOnset + ", inheritanceMode=" + inheritanceMode + ", disorders="
				+ disorders + ", features=" + features + ", genomicFeatures=" + genomicFeatures + "]";
	}

	
	
	
	/**
	 * Returns a JSON representation and keeps out empty fields
	 * @return A JSON string
	 */
	public String getEmptyFieldsRemovedJson(){
		StringBuilder asJson=new StringBuilder();
		asJson.append("{");
		
		//REQUIRED
		asJson.append("\"id\":");
		asJson.append("\"" + this.getId() + "\"");
		//OPTIONAL
		asJson.append(",");
		asJson.append("\"label\":");
		asJson.append("\"" + this.getLabel() + "\"");
		//REQUIRED
		asJson.append(",");
		asJson.append("\"contact\":{");
		int i=0;
		for (String k:this.getContact().keySet()){
			asJson.append("\"" +  k + "\":");
			asJson.append("\"" + this.getContact().get(k) + "\"");
			if (i<this.getContact().size()-1){
				asJson.append(",");
			}
			i+=1;
		}
		asJson.append("}");
		//OPTIONAL
		if (this.getSpecies() != ""){
			asJson.append(",");
			asJson.append("\"species\":");
			asJson.append("\"" + this.getSpecies() + "\"");
		}
		//OPTIONAL
		if (this.getSex() != ""){
			asJson.append(",");
			asJson.append("\"sex\":");
			asJson.append("\"" + this.getSex() + "\"");
		}
		//OPTIONAL
		if (this.getAgeOfOnset() != ""){
			asJson.append(",");
			asJson.append("\"ageOfOnset\":");
			asJson.append("\"" + this.getAgeOfOnset() + "\"");
		}
		//OPTIONAL
		if (this.getInheritanceMode() != ""){
			asJson.append(",");
			asJson.append("\"inheritanceMode\":");
			asJson.append("\"" + this.getInheritanceMode() + "\"");
		}
		//OPTIONAL
		if (this.getDisorders().size()>0){
			asJson.append(",");
			asJson.append("\"disorders\":[");
			int j=0;
			for (Map<String,String> disorder:this.getDisorders()){
				asJson.append("{");
				for (String k:disorder.keySet()){
					asJson.append("\"" +  k + "\":");
					asJson.append("\"" + disorder.get(k) + "\"");
				}
				asJson.append("}");
				if (j<this.getDisorders().size()-1){
					asJson.append(",");
				}
				j+=1;
			}
			asJson.append("]");
		}

		//possibly OPTIONAL
		if (this.getFeatures().size()>0){
			asJson.append(",");
			asJson.append("\"features\":[");
			int k=0;
			for (PhenotypeFeature phenotypeFeature:this.getFeatures()){
				asJson.append(phenotypeFeature.getEmptyFieldsRemovedJson());
				if (k<this.getFeatures().size()-1){
					asJson.append(",");
				}
				k+=1;
			}
			asJson.append("]");
		}

		//possibly OPTIONAL
		if (this.getGenomicFeatures().size()>0){
			asJson.append(",");
			asJson.append("\"genomicFeatures\":[");
			int p=0;
			for (GenomicFeature genomicFeature:this.getGenomicFeatures()){
				asJson.append(genomicFeature.getEmptyFieldsRemovedJson());
				if (p<this.getGenomicFeatures().size()-1){
					asJson.append(",");
				}
				p+=1;
			}
			asJson.append("]");
		}
		
		asJson.append("}");
		return asJson.toString();
	}
	
}