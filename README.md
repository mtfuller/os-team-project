# Phase II Progress

Classes that are complete:
- Loader() writes ProgramFile to paged Disk()
- PCB() has paged buffers
- PageTable() keeps track of each PCB's written pages in RAM, valid/invalid bits
- OS_Driver() no longer has a while loop - now calls LTS once and STS once
- LongTermScheduler() writes each PCB's first four frames of instructions to RAM and then goes to sleep

Currently working on:
- Kernel() - pageFaultQueue and ioQueue have been created
- ShortTermScheduler() - hand off appropriate job to CPUs
- PageManager() has freeFramePool which will track which pages in RAM are available for writing
