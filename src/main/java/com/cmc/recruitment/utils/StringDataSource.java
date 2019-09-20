package com.cmc.recruitment.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;

import javax.activation.DataSource;

class StringDataSource implements DataSource {
    private String contents ;
    private String mimetype ;
    private String name ;


    public StringDataSource( String contents
                           , String mimetype
                           , String name
                           ) {
        this.contents = contents ;
        this.mimetype = mimetype ;
        this.name = name ;
    }

    public String getContentType() {
        return( mimetype ) ;
    }

    public String getName() {
        return( name ) ;
    }

    public InputStream getInputStream() {
        return( new StringBufferInputStream( contents ) ) ;
    }

    public OutputStream getOutputStream() {
        throw new IllegalAccessError( "This datasource cannot be written to" ) ;
    }
} 