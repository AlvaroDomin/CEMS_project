package experimental_properties;

import exceptions.SampleTypeNotFound;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class SampleType {

    public static final Map<String, Integer> MAPSAMPLETYPES;

    static {
        Map<String, Integer> mapChemAlphabetTMP = new LinkedHashMap<>();
        mapChemAlphabetTMP.put("standard", 1);
        mapChemAlphabetTMP.put("plasma", 2);
        mapChemAlphabetTMP.put("urine", 3);
        mapChemAlphabetTMP.put("feces", 4);
        mapChemAlphabetTMP.put("serum", 5);

        // TO DO ALVARO FILL
        MAPSAMPLETYPES = Collections.unmodifiableMap(mapChemAlphabetTMP);
    }

    public static int getSampleTypeIntFromSampleType(String sampleType) throws SampleTypeNotFound {
        Integer sampleTypeInt = MAPSAMPLETYPES.get(sampleType);
        if(sampleTypeInt == null)
        {
            throw new SampleTypeNotFound("Sample type " + sampleType + " NOT FOUND. Check your excel and the nomenclature of sample types. ");
        }
        return sampleTypeInt;
    }

}
