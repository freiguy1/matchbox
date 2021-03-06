package org.broadinstitute.macarthurlab.matchbox.entities;


import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author harindra
 *
 */
public class GenomicFeature {
	/**
	 * REQUIRED
	 * Description of the gene that looks like,
	 * gene" : {
          "id" : <gene symbol>|<ensembl gene ID>|<entrez gene ID>
        }
	 */
	@Indexed
	private final Map<String,String> gene;
	
	/**
	 * OPTIONAL
	 * A description of a variant that looks like,
	 * "variant" : {
          "assembly" : "NCBI36"|"GRCh37.p13"|"GRCh38.p1"|…,
          "referenceName" : "1"|"2"|…|"X"|"Y",
          "start" : <number>,
          "end" : <number>,
          "referenceBases" : "A"|"ACG"|…,
          "alternateBases" : "A"|"ACG"|…
        },
	 */
	private final Variant variant;
	
	private final Long zygosity;
	
	/**
	 * A type value that looks like,
	 *  "type" : {
          "id" : <SO code>,
          "label" : "STOPGAIN"
        }
	 */
	private final Map<String,String> type;

	

	/**
	 * Default constructor
	 * @param gene	gene name
	 * @param variant	variant details
	 * @param zygosity	zygosity
	 * @param type	type
	 */
	public GenomicFeature() {
		this.gene = new HashMap<>();
		this.variant = new Variant();
		this.zygosity = -1L;
		this.type = new HashMap<>();
	}
	
	/**
	 * @param gene	gene name
	 * @param variant	variant details
	 * @param zygosity	zygosity
	 * @param type	type
	 */
	public GenomicFeature(Map<String, String> gene, 
						  Variant variant, 
						  Long zygosity,
						  Map<String, String> type) {
		this.gene = gene;
		this.variant = variant;
		this.zygosity = zygosity;
		this.type = type;
	}
	
	
	/**
	 * @return the variant
	 */
	public Variant getVariant() {
		return this.variant;
	}
	/**
	 * @return the zygosity
	 */
	public Long getZygosity() {
		return zygosity;
	}
	/**
	 * @return the type
	 */
	public Map<String, String> getType() {
		return type;
	}

	/**
	 * Get's gene
	 * @return A gene
	 */
	public final Map<String, String> getGene() {
		return gene;
	}
	
	/* 
	 * To String method(non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GenomicFeature [gene=" + gene + ", variant=" + variant + ", zygosity=" + zygosity + ", type=" + type
				+ "]";
	}


	
	/**
	 * Returns a JSON representation and keeps out empty fields
	 * @return A JSON string
	 */
	public String getEmptyFieldsRemovedJson(){
		StringBuilder asJson=new StringBuilder();
		asJson.append("{");

		if (this.getGene().size()>0){
			asJson.append("\"gene\":{\"id\":");
			asJson.append("\"" + this.getGene().get("id") + "\"");
			asJson.append("}");
		}
		
		if (!this.getVariant().isUnPopulated() && this.getVariant().shareVariantLevelData()){
			asJson.append(",");
			asJson.append("\"variant\":");
			asJson.append(this.getVariant().getEmptyFieldsRemovedJson());
		}
		
		if (this.getZygosity() != 0L && this.getZygosity() != -1){
			asJson.append(",");
			asJson.append("\"zygosity\":");
			asJson.append(this.getZygosity());
		}
		
		if (this.getType().size() > 0) {
			// if at least one of the keys were non-null add a comma
			boolean hasType = false;
			for (String k : this.getType().keySet()) {
				if (this.getType().get(k) != null && !this.getType().get(k).equals("")) {
					asJson.append(",");
					hasType=true;
					break;
				}
			}
			if (hasType) {
				asJson.append("\"type\":{");
				int i=0;
				for (String k2 : this.getType().keySet()) {
					if (this.getType().get(k2) != null && !this.getType().get(k2).equals("")) {
						asJson.append("\"" + k2 + "\":");
						asJson.append("\"" + this.getType().get(k2) + "\"");
						if (i<this.getType().size()-1){
							asJson.append(",");
						}
						i++;
					}
				}
				asJson.append("}");
			}
		}
		asJson.append("}");
		return asJson.toString();
	}

}


