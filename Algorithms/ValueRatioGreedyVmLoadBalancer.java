//Author: Murtaza N

package cloudsim.ext.datacenter;

/**
 * This algorithm prioritizes VMs based on a value-to-resource ratio, inspired by the fractional knapsack problem. 
 * Each VM is assigned a "value" (e.g., based on its processing capability or speed) 
 * and a "resource cost" (e.g., current load or memory usage). 
 * The algorithm selects the available VM with the highest value-to-resource ratio to maximize efficiency.
 * 
 * Best for scenarios where VMs have heterogeneous capabilities (e.g., different CPU speeds or memory capacities).
 */

import java.util.Map;

public class ValueRatioGreedyVmLoadBalancer extends VmLoadBalancer {
    private Map<Integer, VirtualMachineState> vmStatesList;
    private Map<Integer, Double> vmProcessingSpeeds; 

    public ValueRatioGreedyVmLoadBalancer(Map<Integer, VirtualMachineState> vmStatesList,
                                         Map<Integer, Double> vmProcessingSpeeds) {
        super();
        this.vmStatesList = vmStatesList;
        this.vmProcessingSpeeds = vmProcessingSpeeds; 
    }

    @Override
    public int getNextAvailableVm() {
        int selectedVmId = -1;
        double maxRatio = -1.0;

        for (Map.Entry<Integer, VirtualMachineState> entry : vmStatesList.entrySet()) {
            int vmId = entry.getKey();
            VirtualMachineState state = entry.getValue();

            if (state == VirtualMachineState.AVAILABLE) {
                int currentLoad = vmAllocationCounts.getOrDefault(vmId, 0);
                double processingSpeed = vmProcessingSpeeds.getOrDefault(vmId, 1.0);
                double resourceCost = currentLoad + 1; 
                double ratio = processingSpeed / resourceCost;

                if (ratio > maxRatio) {
                    maxRatio = ratio;
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