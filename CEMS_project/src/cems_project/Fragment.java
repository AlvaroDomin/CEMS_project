/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cems_project;

/**
 *
 * @author maria
 */
public class Fragment {

    private final Double m_z;
    private final Double intensity;

    public Fragment(Double m_z) {
        this.m_z = m_z;
        this.intensity = null;
    }

    @Override
    public String toString() {
        return "\t\tM_z (f): " + this.m_z + "\n\t\tIntensity (f): " + this.intensity + "\n";
    }
}
