package com.kennesaw.cpumodule;

import memory.Ram;

import java.util.Arrays;

public class DmaChannel extends Thread {
    private Ram memory;
    private boolean foundValue;
    private char operation;
    private int address;
    private long value;

    public DmaChannel(Ram mem) {
        memory = mem;
        operation = 'X';
        foundValue = false;
    }

    public boolean isValueFound() {
        return foundValue;
    }

    public void signalDMA(char op, int a) {
        signalDMA(op, a, 0L);
    }

    public void signalDMA(char op, int a, long v) {
        operation = op;
        address = a;
        value = v;
        foundValue = false;
        this.notify();
    }

    public long getValue() {
        while(!isValueFound());
        return value;
    }

    private int effectiveAddr(int addr) {
        return addr / 4;
    }

    private long readRAM(int addr) {
        addr = effectiveAddr(addr);
        return memory.readRam(addr);
    }

    private void writeRAM(int addr, long val) {
        addr = effectiveAddr(addr);
        memory.writeRam(addr, val);
    }

    @Override
    public String toString() {
        return "DmaChannel";
    }

    @Override
    public void run() {
        while (true) {
            switch(operation) {
                case 'r':
                    value = readRAM(address);
                    foundValue = true;
                    operation = 'X';
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 'w':
                    writeRAM(address, value);
                    operation = 'X';
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
