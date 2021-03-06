// **********************************************************************
// This file was generated by a TAF parser!
// TAF version 2.1.5.7 by WSRD Tencent.
// Generated from `/usr/local/resin_system.mqq.com/webapps/communication/taf/upload/kennyhong/scp.jce'
// **********************************************************************

package me.longerian.abc.fakeclient;

public final class MHeartBeat extends com.qq.taf.jce.JceStruct implements java.lang.Cloneable
{
    public String className()
    {
        return "jce.MHeartBeat";
    }

    public String fullClassName()
    {
        return "com.tencent.wcs.jce.MHeartBeat";
    }

    public int nSequence = 0;

    public int nInterval = 0;

    public String sPCId = "";

    public int getNSequence()
    {
        return nSequence;
    }

    public void  setNSequence(int nSequence)
    {
        this.nSequence = nSequence;
    }

    public int getNInterval()
    {
        return nInterval;
    }

    public void  setNInterval(int nInterval)
    {
        this.nInterval = nInterval;
    }

    public String getSPCId()
    {
        return sPCId;
    }

    public void  setSPCId(String sPCId)
    {
        this.sPCId = sPCId;
    }

    public MHeartBeat()
    {
    }

    public MHeartBeat(int nSequence, int nInterval, String sPCId)
    {
        this.nSequence = nSequence;
        this.nInterval = nInterval;
        this.sPCId = sPCId;
    }

    public boolean equals(Object o)
    {
        if(o == null)
        {
            return false;
        }

        MHeartBeat t = (MHeartBeat) o;
        return (
            com.qq.taf.jce.JceUtil.equals(nSequence, t.nSequence) && 
            com.qq.taf.jce.JceUtil.equals(nInterval, t.nInterval) && 
            com.qq.taf.jce.JceUtil.equals(sPCId, t.sPCId) );
    }

    public int hashCode()
    {
        try
        {
            throw new Exception("Need define key first!");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }
    public java.lang.Object clone()
    {
        java.lang.Object o = null;
        try
        {
            o = super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return o;
    }

    public void writeTo(com.qq.taf.jce.JceOutputStream _os)
    {
        _os.write(nSequence, 0);
        _os.write(nInterval, 1);
        if (null != sPCId)
        {
            _os.write(sPCId, 2);
        }
    }


    public void readFrom(com.qq.taf.jce.JceInputStream _is)
    {
        this.nSequence = (int) _is.read(nSequence, 0, true);
        this.nInterval = (int) _is.read(nInterval, 1, false);
        this.sPCId =  _is.readString(2, false);
    }

    public void display(java.lang.StringBuilder _os, int _level)
    {
        com.qq.taf.jce.JceDisplayer _ds = new com.qq.taf.jce.JceDisplayer(_os, _level);
        _ds.display(nSequence, "nSequence");
        _ds.display(nInterval, "nInterval");
        _ds.display(sPCId, "sPCId");
    }

    public void displaySimple(java.lang.StringBuilder _os, int _level)
    {
        com.qq.taf.jce.JceDisplayer _ds = new com.qq.taf.jce.JceDisplayer(_os, _level);
        _ds.displaySimple(nSequence, true);
        _ds.displaySimple(nInterval, true);
        _ds.displaySimple(sPCId, false);
    }

}

