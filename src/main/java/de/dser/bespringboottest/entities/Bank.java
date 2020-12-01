package de.dser.bespringboottest.entities;

import com.google.common.collect.Sets;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Bank extends EntityBaseClass {
    @Column(nullable = false)
    private String bankName;
    @Column(nullable = false)
    private int banknumber;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getBanknumber() {
        return banknumber;
    }

    public void setBanknumber(int banknumber) {
        this.banknumber = banknumber;
    }
}
