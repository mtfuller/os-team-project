My Analysis Contributions:

- "Wait Times" are times from creation to being handed off to a CPU for the first time (in LongTermScheduler)
- "Complete Times" are times from creation to completion (in ShortTermScheduler)
- "# of IO" is recorded in the MMU, every time checkForInterrupt is triggered
   - I changed the Analysis code on line 77 to just increment that job's total by one each time
- "CPU ID" - I didn't touch this, not sure what it refers to
- "CPU Space" - I didn't touch this either
- "Ram Space Used" - this is now a percentage reflecting how many pages a job uses out of 256
   - Captured in ShortTermScheduler using the job's page table's length at its life cycle end
- "Memory Utilization" - I removed this column completely, because I think it's redundant, like we talked about today
- "# of PF" - recorded in MMU line 32 whenever a job is moved to the pageFaultQueue
- "Servicing Times" - this adds the net time of (removed from pageFaultQueue - added to pageFaultQueue) and keeps a running total for each time a job has a servicing time. 
   - The recordPageFaultComplete is recorded in PageManager, line 69. The recordPageFaultStart is recorded in MMU, line 33.
- When the metrics are printed, the jobs are now printed in their sorted order.
   
Let me know if I've misunderstood any of these metrics or if I can help with anything else.
