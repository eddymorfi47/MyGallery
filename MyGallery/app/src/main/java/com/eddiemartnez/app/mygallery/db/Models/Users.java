package com.eddiemartnez.app.mygallery.db.Models;

/**
 * Created by Eddie Martínez on 9/4/2018.
 */

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import com.eddiemartnez.app.mygallery.db.GaleriaDB;

/**
 * Created by Brian on 3/26/18.
 */
@Table(database = GaleriaDB.class)
public class Users  extends BaseModel{
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String username;

    @Column
    public String password;


    @Column
    public String nombre;

    @Column
    public String roll;

}