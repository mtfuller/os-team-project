# Phase II Progress

Classes that are complete:
<br>&#10004; Loader() writes ProgramFile to paged Disk()
<br>&#10004; PCB() has paged buffers
<br>&#10004; PageTable() keeps track of each PCB's written pages in RAM, valid/invalid bits
<br>&#10004; OS_Driver() no longer has a while loop - now calls LTS once and STS once
<br>&#10004; LongTermScheduler() writes each PCB's first four frames of instructions to RAM and then goes to sleep

Currently working on:
<ul style="list-style-type:square">
<li>Kernel() - pageFaultQueue and ioQueue have been created</li>
<li>ShortTermScheduler() - hand off appropriate job to CPUs</li>
<li>PageManager() has freeFramePool which will track which pages in RAM are available for writing</li>
<ul style="list-style-type:disc">
    <li> has toString() method which lists pages in RAM that are available</li>
    <li> has cleanPageTable() method which dumps a PCB's written pages back into the freeFramePool when the job's been run to completion</li>
    <li> </li>
    <li> </li>
    <li> </li>
Things to note:
- the RAM() object now takes a PageManager() as a parameter. All of the files in this branch have been updated to reflect that.
