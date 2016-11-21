# Phase II Progress

<b>Classes that are complete:</b>
<br>
<br>&#10004; Loader() writes ProgramFile to paged Disk()
<br>&#10004; PCB() has paged buffers
<br>&#10004; PageTable() keeps track of each PCB's written pages in RAM, valid/invalid bits
<br>&#10004; OS_Driver() no longer has a while loop - now calls LTS once and STS once
<br>&#10004; LongTermScheduler() writes each PCB's first four frames of instructions to RAM and then goes to sleep

<b>Currently working on:</b>
<br>
<br>Kernel():
- pageFaultQueue and ioQueue have been created
ShortTermScheduler():
- hand off appropriate job to CPUs

PageManager():
- has freeFramePool which will track which pages in RAM are available for writing
- has toString() method which lists pages in RAM that are available
- has cleanPageTable() method which dumps a PCB's written pages back into the freeFramePool when the job's been run to completion

<br><b>Things to note:</b>
- the RAM() object now takes a PageManager() as a parameter. All of the files in this branch have been updated to reflect that.
- the findCPU() method in the STS isn't working, and I'm not sure what the problem is. I think the Dma requires an MMU as a parameter, but I'm not sure where to declare that?
