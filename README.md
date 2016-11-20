<<<<<<< HEAD
#Phase II Progress

These are the classes that I believe are complete:
 - Loader() writes the ProgramFile to Disk()
 - Each PCB() now has pages to represent its 3 buffers
 - Each PCB() has a PageTable(), which is a collection of two arrays
 - LongTerm Scheduler() moves each PCB's 1st four Disk pages to RAM and then goes to sleep

At this point, the program stops after the LTS goes to sleep. When the program is run, <br>a copy of the Disk() and RAM() are printed, as is each PCB's PageTable().
=======
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
>>>>>>> origin/Phase_2
