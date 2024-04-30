package com.example.hello_world_with_mvc.entity;

public class Group {
    private String gid;
    private String groupname;
    private String cid;
    private String number;

    public Group(){
        this.gid = null;
        this.cid = null;
        this.groupname = null;
        this.number = null;
   
    }

    public String getcid(){
        return this.cid;
    }
    public String getgroupname(){
        return this.groupname;
    }
    public String getnumber(){
        return this.number;
    }
    public void setgroupname(String groupname){
        this.groupname = groupname;
    }
    public void setcid(String cid){
        this.cid = cid;
    }
    public void setnumber(String number){
        this.number = number;
    }
    public void setgid(String gid){
        this.gid = gid;
    }
}
