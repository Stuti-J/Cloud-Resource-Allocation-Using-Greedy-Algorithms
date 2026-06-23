//Author: Murtaza N

/**
 * The algorithm selects the VM that will be available the soonest, 
 * based on the estimated completion time of its current tasks. 
 * It assumes tasks have known processing times and prioritizes VMs that can start processing new tasks earliest.
 * Ideal for minimizing waiting time in scenarios with variable task durations.
 */

package cloudsim.ext.datacenter;

import java.util.Map;

public class EarliestAvailableGreedyVmLoadBalancer extends VmLoadBalancer {
    private Map<Integer, VirtualMachineState> vmStatesList;
    private Map<Integer, Double> vmEstimatedCompletionTimes; 

    public EarliestAvailableGreedyVmLoadBalancer(Map<Integer, VirtualMachineState> vmStatesList,
                                                Map<Integer, Double> vmEstimatedCompletionTimes) {
        super();
        this.vmStatesList = vmStatesList;
        this.vmEstimatedCompletionTimes = vmEstimatedCompletionTimes; 
    }

    @Override
    public int getNextAvailableVm() {
        int selectedVmId = -1;
        double earliestTime = Double.MAX_VALUE;

        for (Map.Entry<Integer, VirtualMachineState> entry : vmStatesList.entrySet()) {
            int vmId = entry.getKey();
            VirtualMachineState state = entry.getValue();

            if (state == VirtualMachineState.AVAILABLE) {
                double completionTime = vmEstimatedCompletionTimes.getOrDefault(vmId, 0.0);
                if (completionTime < earliestTime) {
                    earliestTime = completionTime;
                    selectedVmId = vmId;
                }
            }
        }

        if (selectedVmId != -1) {
            allocatedVm(selectedVmId);
        }

        return selectedVmId;
    }
}