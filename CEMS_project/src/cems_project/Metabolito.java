/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cems_project;

import java.util.List;

/**
 *
 * @author maria
 */
public class Metabolito {

    private final String compound;
    private final String formula;
    private final Double M;
    private final Double m_z;
    private final Double MTcompnd;
    private final Double MTmets;
    private final Double RMTmets;
    private final Double MTmes;
    private final Double RMTmes;
    private final List<Double> fragments;

    //constructor
    public Metabolito(String compound, String formula, Double M, Double m_z, Double MTcompnd, Double MTmets, Double RMTmets, Double MTmes, Double RMTmes, List<Double> fragments) {
        this.compound = compound;
        this.formula = formula;
        this.M = M;
        this.m_z = m_z;
        this.MTcompnd = MTcompnd;
        this.MTmets = MTmets;
        this.RMTmets = RMTmets;
        this.MTmes = MTmes;
        this.RMTmes = RMTmes;
        this.fragments = fragments;
    }

    @Override
    public String toString() {
        String human_readable_string = "COMPOUND: " + this.compound + "\n\tFORMULA: " + this.formula + "\n\tM: " + this.M + "\n\tm_z: "
                + this.m_z + "\n\tMTcompound: " + this.MTcompnd + "\n\tMTmets: " + this.MTmets + "\n\tRMTmets: "
                + this.RMTmets + "\n\tMTmes: " + this.MTmes + "\n\tRMTmes: " + this.RMTmes + "\n\tFragments: " + this.fragments + "\n\n";
        return human_readable_string;
    }

}
