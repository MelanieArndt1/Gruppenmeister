package com.example.gruppenmeister.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Date

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "Gruppenmeister_db", factory, 1) {


    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE Benutzer (" +
                "benutzerid INTEGER PRIMARY KEY," +
                "name TEXT," +
                "rolle TEXT," +
                "passwort TEXT);" +
                "CREATE TABLE Gruppe (" +
                "gruppenid INTEGER PRIMARY KEY," +
                "name TEXT);" +
                "CREATE TABLE istmitglied(" +
                "benutzerid INTEGER," +
                "gruppenid INTEGER," +
                "PRIMARY KEY(benutzerid, gruppenid)," +
                "FOREIGN KEY(benutzerid) REFERENCES Benutzer(benutzerid)," +
                "FOREIGN KEY(gruppenid) REFERENCES Gruppe(gruppenid));" +
                "CREATE TABLE Aufgabe (" +
                "aufgabenid INTEGER PRIMARY KEY," +
                "name TEXT," +
                "beginnt DATETIME," +
                "endet DATETIME," +
                "beschreibung TEXT," +
                "prio INTEGER," +
                "benutzerid INTEGER," +
                "gruppenid INTEGER," +
                "FOREIGN KEY(benutzerid) REFERENCES Benutzer(benutzerid)," +
                "FOREIGN KEY(gruppenid) REFERENCES Gruppe(gruppenid));" +
                "CREATE TABLE Termin (" +
                "termindid INTEGER PRIMARY KEY," +
                "name TEXT," +
                "datum DATETIME," +
                "notiz TEXT," +
                "gruppenid INTEGER" +
                "FOREIGN KEY(gruppenid) REFERENCES Gruppe(gruppenid));" +
                "CREATE TABLE Dokument (" +
                "dokumentid INTEGER PRIMARY KEY," +
                "ttl DATETIME" +
                "gruppenid INTEGER," +
                "aufgabenid INTEGER," +
                "FOREIGN KEY(gruppenid) REFERENCES Gruppe(gruppenid)," +
                "FOREIGN KEY(aufgabenidid) REFERENCES Aufgabe(aufgabenid));")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS BENUTZER")
        onCreate(db)
    }

    fun addBenutzer(benutzerid: Int, name: String, rolle: String, passwort:String){
        val values = ContentValues()

        values.put("benutzerid", benutzerid)
        values.put("name",name)
        values.put("rolle", rolle)
        values.put("passwort",passwort)

        val db=this.writableDatabase

        db.insert("Benutzer",null,values)

        db.close()
    }

    fun addGruppe(gruppenid: Int, name: String){
        val values = ContentValues()

        values.put("gruppenid", gruppenid)
        values.put("name", name)

        val db=this.writableDatabase

        db.insert("Gruppe",null,values)

        db.close()
    }

    fun addistMitglied(benutzerid: Int, gruppenid: Int){
        val values = ContentValues()
        values.put("benutzerid", benutzerid)
        values.put("gruppenid", gruppenid)

        val db=this.writableDatabase

        db.insert("istmitglied", null, values)

        db.close()
    }

    fun addTermin(terminid: Int, name:String, datum: Date, notiz: String, gruppenid: Int ) {
        val values = ContentValues()

        values.put("terminid", terminid)
        values.put("name", name)
        values.put("datum", datum.toString())
        values.put("notiz", notiz)
        values.put("gruppenid", gruppenid)

        val db= this.writableDatabase
        db.insert("Termin", null, values)

        db.close()
    }

    fun addAufgabe(aufgabenid: Int, name:String, beginnt: Date, endet: Date, beschreibung: String, prio: Int, gruppenid: Int, benutzerid: Int) {
        val values = ContentValues()

        values.put("aufgabenid", aufgabenid)
        values.put("name", name)
        values.put("beginnt", beginnt.toString())
        values.put("endet", endet.toString())
        values.put("beschreibung", beschreibung)
        values.put("prio", prio)
        values.put("gruppenid", gruppenid)
        values.put("benutzerid", benutzerid)

        val db = this.writableDatabase
        db.insert("Aufgabe", null, values)

        db.close()
    }

    fun addDokument(dokumentid: Int, ttl: Date, gruppenid: Int, aufgabenid: Int) {
        val values = ContentValues()

        values.put("dokumentid", dokumentid)
        values.put("ttl", ttl.toString())
        values.put("gruppenid",gruppenid)
        values.put("aufgabenid", aufgabenid)

        val db= this.writableDatabase
        db.insert("Dokument", null, values)

        db.close()
    }

    fun getBenutzer(): Cursor? {
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM Benutzer", null)
    }

    fun getBenutzerid(name: String): Cursor? {
        val db = this.readableDatabase

        return db.rawQuery("SELECT benutzerid FROM Benutzer WHERE name = ?", arrayOf(name))
    }

    fun getBenutzername(benutzerid: Int): Cursor? {
        val db = this.readableDatabase

        return db.rawQuery("SELECT name FROM Benutzer Where benutzerid = ?", arrayOf(benutzerid.toString()))
    }

    fun getGruppen(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * From Gruppe", null)
    }

    fun getmeineGruppen(benutzerid: Int): Cursor? {
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM Gruppe INNER JOIN istmitglied ON Gruppe.gruppenid = istmitglied.gruppenid Where istmitglied.benutzerid = ? ", arrayOf(benutzerid.toString()))
    }

    fun getAufgaben(): Cursor? {
        val db = this.readableDatabase

        return db.rawQuery("SELECT * From Aufgabe", null)
    }

    fun getmeineAufgaben(benutzerid: Int): Cursor? {
        val db= this.readableDatabase

        return db.rawQuery("SELECT* From Aufgabe where benutzerid = ?",arrayOf(benutzerid.toString()))
    }


    fun getTermine():Cursor? {
        val db= this.readableDatabase

        return db.rawQuery("SELECT * From Termin", null)
    }

    fun getDokumente():Cursor? {
        val db= this.readableDatabase

        return db.rawQuery("SELECT * FROM Dokument", null)
    }

}