
insert into METABOLITES
(COMPOUND_NAME, FORMULA, MONOISOTOPIC_MASS, M_Z, MT_COMPND, MT_METS,
RMT_METS, MT_MES, RMT_MES)
VALUES ('2,3-Diphosphoglyceric acid', 'C3H8O10P2',
		265.9593, 264.9520, 8.820, 30.379, 0.29, 21.515, 0.41),
		('Fructose 1,6 Biphosphate', 'C6H14O12P2', 
		339.9960, 338.9887, 9.739, 27.135, 0.36, 20.187, 0.48);

/*el ID se va poniendo solo porque es autoincrement, yo no tengo que pasarlo como parámetro*/       
        
        
SELECT @metabolite_id := id FROM metabolites WHERE COMPOUND_NAME='Fructose 1,6 Biphosphate';

/* creamos una variable metabolite_id para saber qué ID tiene el COMPOUND que tiene fragmentos*/



insert into FRAGMENTS  /* ahora metemos los fragmentos pasandole el ID que tenemos almecenado en la variable*/ 
(ID_MET, M_Z)
VALUES (@metabolite_id, 78.9594),
		(@metabolite_id, 96.9671), 
        (@metabolite_id, 138.9802),
        (@metabolite_id, 150.9799),
        (@metabolite_id, 158.9255),
        (@metabolite_id, 168.9910),
        (@metabolite_id, 176.9359),
        (@metabolite_id, 241.0115),
        (@metabolite_id, 338.9880);
 
 
 
/*si no le pasamos el mismo numero de datos que le estamos especificando, salta un error*/






