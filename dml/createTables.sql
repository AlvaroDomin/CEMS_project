
create database IF NOT EXISTS CEMS;
use CEMS;

drop table IF EXISTS compounds;
drop table IF EXISTS compound_identifiers;
drop table IF EXISTS compounds_hmdb;
drop table IF EXISTS compounds_pc;
drop table IF EXISTS ce_experimental_properties;
drop table IF EXISTS ce_eff_mob;
drop table IF EXISTS ce_experimental_properties_metadata;
drop table IF EXISTS compound_ce_product_ion;


-- COMPOUND TYPE: 0 for metabolites, 1 for lipids, 2 for peptides, 3 for oxidized compounds
CREATE TABLE compounds (
  compound_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  cas_id varchar(100) UNIQUE DEFAULT NULL,
  compound_name text not null,
  formula varchar(100) DEFAULT '',
  mass double DEFAULT 0,
  charge_type int default 0, -- charge 0 for neutral, 1 for positive 2 for negative
  charge_number int default 0, -- number of charges (negative or positive)
  formula_type varchar(20) DEFAULT NULL, -- CHNOPS, CHNOPSD, CHNOPSCL, CHNOPSCLD, ALLD or ALL
  formula_type_int int, -- 0 CHNOPS, 1 CHNOPSD, 2 CHNOPSCL, 3 CHNOPSCLD, 4 ALL, 5 ALLD
  compound_type int default 0, -- type of compound: 0 for metabolite, 1 for lipids, 2 for peptide
  compound_status int default 0, -- status of compound: 0 expected, 1 detected, 2 quantified, 3 predicted (HMDB)
  logP double default null, -- LogP of the compound. By default null
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX compounds_cas_id_index (cas_id),
  INDEX compounds_formula_index (formula),
  INDEX compounds_mass_index (mass),
  INDEX compounds_charge_type_index (charge_type),
  INDEX compounds_formula_type_index (formula_type),
  INDEX compounds_formula_type_int_index (formula_type_int),
  INDEX compounds_compound_type_index (compound_type),
  INDEX compounds_compound_status_index (compound_status),
  INDEX compounds_logP_index (logP)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE compound_identifiers (
  compound_id INT NOT NULL,
  inchi varchar(1800) DEFAULT '',
  inchi_key varchar(50) DEFAULT '' UNIQUE NOT NULL,
  smiles varchar(1200) DEFAULT '',
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (compound_id) REFERENCES compounds(compound_id) on DELETE CASCADE,
  CONSTRAINT pk_inchi PRIMARY KEY (compound_id),
  INDEX inchi_key_index (inchi_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE compounds_hmdb (
  compound_id INT NOT NULL,
  hmdb_id varchar(100) NOT NULL,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (compound_id) REFERENCES compounds(compound_id) on DELETE CASCADE,
  CONSTRAINT pk_compounds_hmdb PRIMARY KEY (compound_id,hmdb_id),
  INDEX hmdb_id_index (hmdb_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE compounds_pc (
  compound_id INT NOT NULL,
  pc_id INT NOT NULL,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (compound_id) REFERENCES compounds(compound_id) on DELETE CASCADE,
  CONSTRAINT pk_compounds_pc PRIMARY KEY (compound_id,pc_id),
  INDEX pc_id_index (pc_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table ce_experimental_properties(
  ce_exp_prop_id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  buffer int not null, -- 1: formico 1 molar (1M) al 10% de methanol, 2: acetic acid 10%
  temperature int not null , -- temperature in celsius degrees
  ionization_mode INT NOT NULL default 1,  -- 1: positive 2: negative
  polarity int not null, -- 1: direct, 2: inverse
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  UNIQUE KEY `experimental_properties` (`buffer`,`temperature`,`ionization_mode`,`polarity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
create table ce_eff_mob(
  ce_eff_mob_id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ce_compound_id INT NOT NULL,
  ce_exp_prop_id int(11) NOT NULL,
  cembio_id INT NULL, -- INTERNAL ID FROM CEMBIO TO RELATE WITH THE PRODUCT IONS
  eff_mobility double default null, -- eff mobility By default null
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  CONSTRAINT ce_compound_id_ce_eff_mob_constraint FOREIGN KEY (ce_compound_id) REFERENCES compounds(compound_id) on DELETE CASCADE,
  CONSTRAINT ce_exp_prop_id_ce_eff_mob_constraint FOREIGN KEY (ce_exp_prop_id) REFERENCES ce_experimental_properties(ce_exp_prop_id) on DELETE CASCADE,
  UNIQUE KEY `eff_mob_key` (`ce_compound_id`,`ce_exp_prop_id`), 
  INDEX eff_mob_index (eff_mobility),
  INDEX eff_mob_ce_exp_prop_id_index (ce_exp_prop_id),
  INDEX eff_mob_ce_exp_prop_id_eff_mob_index (ce_exp_prop_id, eff_mobility),
  INDEX eff_mob_compound_id_index (ce_compound_id), 
  INDEX eff_mob_cembio_id_index (cembio_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table ce_experimental_properties_metadata(
  ce_ms_exp_prop_metadata_id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ce_eff_mob_id INT(11) NOT NULL, 
  experimental_mz double default null, -- experimental mz obtained in the analysis
  ce_identification_level int not null default 1, -- 1: level 1 (standard), 2: L2 and 3: L3 (not used here)
  ce_sample_type int NOT NULL default 1, -- 1: standard, 2: plasma, 3: urine, 4: feces, etc.
  capillary_length int not null default 1000, -- capillary length in mm (1000)
  capillary_voltage int not null default 30, -- capillary voltage in kV // TODO DEFAULT
  bge_compound_id int default null, -- 180838: methionine sulfone, 2: 73414
  absolute_MT double default null, 
  relative_MT double default null, -- regarding bge (paracetamol or methionine sulfone)
  commercial varchar(20) default null, 
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  CONSTRAINT ce_eff_mob_id_ce_experimental_properties_metadata_constraint FOREIGN KEY (ce_eff_mob_id) REFERENCES ce_eff_mob(ce_eff_mob_id) on DELETE CASCADE,
  CONSTRAINT bge_compound_id_constraint FOREIGN KEY (bge_compound_id) REFERENCES ce_eff_mob(ce_compound_id) on DELETE CASCADE,
  INDEX ce_experimental_mz_index (experimental_mz), 
  INDEX ce_identification_level_index (ce_identification_level), 
  INDEX ce_sample_type_index (ce_sample_type), 
  INDEX ce_bge_MT_index (bge_compound_id), 
  INDEX ce_absolute_MT_index (absolute_MT), 
  INDEX ce_relative_MT_index (relative_MT)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table compound_ce_product_ion (
  ce_product_ion_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
  ion_source_voltage INT default NULL, -- ion_source_voltage in the ionization source
  ce_product_ion_mz double not null default 0, 
  ce_product_ion_intensity double default null, 
  ce_transformation_type varchar(20) not null, -- Adduct, fragment, etc.
  ce_product_ion_name varchar(100), 
  ce_eff_mob_id int(11) NOT NULL,
  compound_id_own INT, -- Identification of the fragment
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  CONSTRAINT compound_ce_product_ion_compound_id_constraint FOREIGN KEY (ce_eff_mob_id) REFERENCES ce_eff_mob(ce_eff_mob_id) on DELETE CASCADE,
  CONSTRAINT compound_id_own_constraint FOREIGN KEY (compound_id_own) REFERENCES compounds(compound_id) on delete set null, 
  UNIQUE KEY `compound_ce_product_ion_key` (`ion_source_voltage`,`ce_product_ion_mz`,`ce_eff_mob_id`), 
  INDEX cepi_mz_index (ce_product_ion_mz), 
  INDEX cepi_peak_intensity_index (ce_product_ion_intensity),
  INDEX cepi_ce_transformation_type_index (ce_transformation_type),
  INDEX cepi_ce_eff_mob_id_id_index (ce_eff_mob_id),
  INDEX cepi_ion_source_voltage_index (ion_source_voltage)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (1,20,1,1);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (1,20,1,2);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (1,20,2,1);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (1,20,2,2);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (1,25,1,1);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (1,25,1,2);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (1,25,2,1);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (1,25,2,2);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (2,20,1,1);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (2,20,1,2);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (2,20,2,1);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (2,20,2,2);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (2,25,1,1);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (2,25,1,2);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (2,25,2,1);
insert into ce_experimental_properties(buffer,temperature, ionization_mode, polarity) VALUES (2,25,2,2);

INSERT IGNORE INTO ce_eff_mob(ce_compound_id, ce_exp_prop_id, cembio_id, eff_mobility) VALUES(180838, 1, 123, 774.7394);
INSERT IGNORE INTO ce_eff_mob(ce_compound_id, ce_exp_prop_id, cembio_id, eff_mobility) VALUES(73414, 1, 237, 0);
INSERT IGNORE INTO ce_experimental_properties_metadata(ce_eff_mob_id, experimental_mz, ce_identification_level, ce_sample_type, capillary_voltage, capillary_length, bge_compound_id, absolute_MT, relative_MT, commercial) VALUES(1, null, 1, 1, 30, 1000, 180838, 14.242, 1, 'SIGMA-ALDRICH');
INSERT IGNORE INTO ce_experimental_properties_metadata(ce_eff_mob_id, experimental_mz, ce_identification_level, ce_sample_type, capillary_voltage, capillary_length, bge_compound_id, absolute_MT, relative_MT, commercial) VALUES(1, null, 1, 1, 30, 1000, 73414, null, 0.66, 'SIGMA-ALDRICH');
INSERT IGNORE INTO ce_experimental_properties_metadata(ce_eff_mob_id, experimental_mz, ce_identification_level, ce_sample_type, capillary_voltage, capillary_length, bge_compound_id, absolute_MT, relative_MT, commercial) VALUES(2, 152.071, 1, 1, 30, 1000, 180838, 20.8669, 1.48740118753163, 'SIGMA-ALDRICH');
INSERT IGNORE INTO ce_experimental_properties_metadata(ce_eff_mob_id, experimental_mz, ce_identification_level, ce_sample_type, capillary_voltage, capillary_length, bge_compound_id, absolute_MT, relative_MT, commercial) VALUES(2, 152.071, 1, 1, 30, 1000, 73414, null, 1, 'SIGMA-ALDRICH');


