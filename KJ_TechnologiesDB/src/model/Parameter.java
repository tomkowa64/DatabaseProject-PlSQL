/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Tomasz
 */
public class Parameter {
    private int ParameterID;
    private String Model;
    private String Type;
    private double Clock;
    private String Socket;
    private String Chipset;
    private int NumOfCores;
    private int NumOfThreads;
    private double Capacity;
    private int Cache;
    private int Vram;
    private String Format;
    private String Interface;
    private String Inputs;
    private int ReadSpeed;
    private int WriteSpeed;
    private int MTBF;
    private int TDP;
    private String Color;
    private int Height;
    private int Width;
    private int Length;
    private String Accessories;
    private int Warranty;
    private int MaxPower;
    private String ProtectionType;

    public Parameter() {
    }

    public Parameter(int ParameterID, String Model, String Type, double Clock, String Socket, String Chipset, int NumOfCores, int NumOfThreads, double Capacity, int Cache, int Vram, String Format, String Interface, String Inputs, int ReadSpeed, int WriteSpeed, int MTBF, int TDP, String Color, int Height, int Width, int Length, String Accessories, int Warranty, int MaxPower, String ProtectionType) {
        this.ParameterID = ParameterID;
        this.Model = Model;
        this.Type = Type;
        this.Clock = Clock;
        this.Socket = Socket;
        this.Chipset = Chipset;
        this.NumOfCores = NumOfCores;
        this.NumOfThreads = NumOfThreads;
        this.Capacity = Capacity;
        this.Cache = Cache;
        this.Vram = Vram;
        this.Format = Format;
        this.Interface = Interface;
        this.Inputs = Inputs;
        this.ReadSpeed = ReadSpeed;
        this.WriteSpeed = WriteSpeed;
        this.MTBF = MTBF;
        this.TDP = TDP;
        this.Color = Color;
        this.Height = Height;
        this.Width = Width;
        this.Length = Length;
        this.Accessories = Accessories;
        this.Warranty = Warranty;
        this.MaxPower = MaxPower;
        this.ProtectionType = ProtectionType;
    }

    public int getParameterID() {
        return ParameterID;
    }

    public void setParameterID(int ParameterID) {
        this.ParameterID = ParameterID;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public double getClock() {
        return Clock;
    }

    public void setClock(double Clock) {
        this.Clock = Clock;
    }

    public String getSocket() {
        return Socket;
    }

    public void setSocket(String Socket) {
        this.Socket = Socket;
    }

    public String getChipset() {
        return Chipset;
    }

    public void setChipset(String Chipset) {
        this.Chipset = Chipset;
    }

    public int getNumOfCores() {
        return NumOfCores;
    }

    public void setNumOfCores(int NumOfCores) {
        this.NumOfCores = NumOfCores;
    }

    public int getNumOfThreads() {
        return NumOfThreads;
    }

    public void setNumOfThreads(int NumOfThreads) {
        this.NumOfThreads = NumOfThreads;
    }

    public double getCapacity() {
        return Capacity;
    }

    public void setCapacity(double Capacity) {
        this.Capacity = Capacity;
    }

    public int getCache() {
        return Cache;
    }

    public void setCache(int Cache) {
        this.Cache = Cache;
    }

    public int getVram() {
        return Vram;
    }

    public void setVram(int Vram) {
        this.Vram = Vram;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String Format) {
        this.Format = Format;
    }

    public String getInterface() {
        return Interface;
    }

    public void setInterface(String Interface) {
        this.Interface = Interface;
    }

    public String getInputs() {
        return Inputs;
    }

    public void setInputs(String Inputs) {
        this.Inputs = Inputs;
    }

    public int getReadSpeed() {
        return ReadSpeed;
    }

    public void setReadSpeed(int ReadSpeed) {
        this.ReadSpeed = ReadSpeed;
    }

    public int getWriteSpeed() {
        return WriteSpeed;
    }

    public void setWriteSpeed(int WriteSpeed) {
        this.WriteSpeed = WriteSpeed;
    }

    public int getMTBF() {
        return MTBF;
    }

    public void setMTBF(int MTBF) {
        this.MTBF = MTBF;
    }

    public int getTDP() {
        return TDP;
    }

    public void setTDP(int TDP) {
        this.TDP = TDP;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int Height) {
        this.Height = Height;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int Width) {
        this.Width = Width;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int Length) {
        this.Length = Length;
    }

    public String getAccessories() {
        return Accessories;
    }

    public void setAccessories(String Accessories) {
        this.Accessories = Accessories;
    }

    public int getWarranty() {
        return Warranty;
    }

    public void setWarranty(int Warranty) {
        this.Warranty = Warranty;
    }

    public int getMaxPower() {
        return MaxPower;
    }

    public void setMaxPower(int MaxPower) {
        this.MaxPower = MaxPower;
    }

    public String getProtectionType() {
        return ProtectionType;
    }

    public void setProtectionType(String ProtectionType) {
        this.ProtectionType = ProtectionType;
    }

    @Override
    public String toString() {
        return "Parameter{" + "ParameterID=" + ParameterID + ", Model=" + Model + ", Type=" + Type + ", Clock=" + Clock + ", Socket=" + Socket + ", Chipset=" + Chipset + ", NumOfCores=" + NumOfCores + ", NumOfThreads=" + NumOfThreads + ", Capacity=" + Capacity + ", Cache=" + Cache + ", Vram=" + Vram + ", Format=" + Format + ", Interface=" + Interface + ", Inputs=" + Inputs + ", ReadSpeed=" + ReadSpeed + ", WriteSpeed=" + WriteSpeed + ", MTBF=" + MTBF + ", TDP=" + TDP + ", Color=" + Color + ", Height=" + Height + ", Width=" + Width + ", Length=" + Length + ", Accessories=" + Accessories + ", Warranty=" + Warranty + ", MaxPower=" + MaxPower + ", ProtectionType=" + ProtectionType + '}';
    }
    
    
}
