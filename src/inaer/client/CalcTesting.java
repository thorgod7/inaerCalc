package inaer.client;

import com.google.gwt.junit.client.GWTTestCase;

import inaer.client.calculator.Controller;
import inaer.client.calculator.Model;
import inaer.client.calculator.Controller.ECalcCmds;

public class CalcTesting extends GWTTestCase {
  private Model calcModel;
  private Controller calcCtrl;
  
  @Override
  public String getModuleName() {
    return "inaer.Calc";
  }
  
  private void checkInit() {
    if(calcModel == null)
      calcModel = new Model();
    if(calcCtrl == null)
      calcCtrl = new Controller(calcModel);
  }
    
  public void testInteger() {
    checkInit();
    calcCtrl.processCmd(ECalcCmds.clear);
    calcCtrl.processCmd(ECalcCmds.v0);
    calcCtrl.processCmd(ECalcCmds.v2);
    calcCtrl.processCmd(ECalcCmds.v4);
    calcCtrl.processCmd(ECalcCmds.v6);
    assertEquals("Value should be 246", "246", calcCtrl.getCurrentValue());
  }
  
  public void testFloat() {
    checkInit();
    calcCtrl.processCmd(ECalcCmds.clear);
    calcCtrl.processCmd(ECalcCmds.v0);
    calcCtrl.processCmd(ECalcCmds.v2);
    calcCtrl.processCmd(ECalcCmds.v4);
    calcCtrl.processCmd(ECalcCmds.v6);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v4);
    assertEquals("Value should be 246", "246.4", calcCtrl.getCurrentValue());
  }
  
  public void testAdd() {
    checkInit();
    calcCtrl.processCmd(ECalcCmds.clear);    
    calcCtrl.processCmd(ECalcCmds.v6);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v4);
    calcCtrl.processCmd(ECalcCmds.add);
    calcCtrl.processCmd(ECalcCmds.v3);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v6);
    calcCtrl.processCmd(ECalcCmds.equal);
    assertEquals("Value should be 10", "10", calcCtrl.getCurrentValue());
  }
  
  public void testAddF() {
    checkInit();
    calcCtrl.processCmd(ECalcCmds.clear);    
    calcCtrl.processCmd(ECalcCmds.v6);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v4);
    calcCtrl.processCmd(ECalcCmds.add);
    calcCtrl.processCmd(ECalcCmds.v3);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v8);
    calcCtrl.processCmd(ECalcCmds.equal);
    assertEquals("Value should be 10.2", "10.2", calcCtrl.getCurrentValue());
  }
  
  public void testSubstract() {
    checkInit();
    calcCtrl.processCmd(ECalcCmds.clear);    
    calcCtrl.processCmd(ECalcCmds.v6);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v4);
    calcCtrl.processCmd(ECalcCmds.substract);
    calcCtrl.processCmd(ECalcCmds.v3);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v8);
    calcCtrl.processCmd(ECalcCmds.equal);
    assertEquals("Value should be 2.6", "2.6", calcCtrl.getCurrentValue());
  }
  
  public void testMultiply() {
    checkInit();
    calcCtrl.processCmd(ECalcCmds.clear);    
    calcCtrl.processCmd(ECalcCmds.v6);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v4);
    calcCtrl.processCmd(ECalcCmds.multiply);
    calcCtrl.processCmd(ECalcCmds.v3);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v8);
    calcCtrl.processCmd(ECalcCmds.equal);
    assertEquals("Value should be 24.32", "24.32", calcCtrl.getCurrentValue());
  }
  
  public void testDivide() {
    checkInit();
    calcCtrl.processCmd(ECalcCmds.clear);    
    calcCtrl.processCmd(ECalcCmds.v6);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v4);
    calcCtrl.processCmd(ECalcCmds.divide);
    calcCtrl.processCmd(ECalcCmds.v3);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v8);
    calcCtrl.processCmd(ECalcCmds.equal);
    assertEquals("Value should be 1.684", "1.684", calcCtrl.getCurrentValue());
  }
  
  public void testPercent() {
    checkInit();
    calcCtrl.processCmd(ECalcCmds.clear);    
    calcCtrl.processCmd(ECalcCmds.v6);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.v4);
    calcCtrl.processCmd(ECalcCmds.multiply);
    calcCtrl.processCmd(ECalcCmds.v5);
    calcCtrl.processCmd(ECalcCmds.dot);
    calcCtrl.processCmd(ECalcCmds.percent);
    assertEquals("Value should be 0.32", "0.32", calcCtrl.getCurrentValue());
  }
}
