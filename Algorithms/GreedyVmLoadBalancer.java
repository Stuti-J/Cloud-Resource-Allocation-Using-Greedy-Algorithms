//Author: Murtaza N

package cloudsim.ext.datacenter;

import java.util.Map;

/**
 * This class implements a greedy load balancing strategy for VMs.
 * It selects the VM with the least current load (fewest allocated tasks)
 * from the available VMs.
 */
public class GreedyVmLoadBalancer extends VmLoadBalancer {

    private Map<Integer, VirtualMachineState> vmStatesList;

    /**
     * Constructor
     *
     * @param vmStatesList The map of VM states
     */
    public GreedyVmLoadBalancer(Map<Integer, VirtualMachineState> vmStatesList) {
        super();
        this.vmStatesList = vmStatesList;
    }

    /**
     * Selects the next available VM with the least current load.
     *
     * @return The ID of the selected VM, or -1 if no VM is available
     */
    @Override
    public int getNextAvailableVm() {
        int selectedVmId = -1;
        int minLoad = Integer.MAX_VALUE;

        // Iterate through all VMs to find the available one with the least load
        for (Map.Entry<Integer, VirtualMachineState> entry : vmStatesList.entrySet()) {
            int vmId = entry.getKey();
            VirtualMachineState state = entry.getValue();

            // Only consider available VMs
            if (state == VirtualMachineState.AVAILABLE) {
                int currentLoad = vmAllocationCounts.getOrDefault(vmId, 0);
                if (currentLoad < minLoad) {
                    minLoad = currentLoad;
                    selectedVmId = vmId;
                }
            }
        }

        // If a VM is found, update its allocation count
        if (selectedVmId != -1) {
            allocatedVm(selectedVmId);
        }

        return selectedVmId;
    }
}