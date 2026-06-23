//package cloudsim.ext.datacenter;
//
//import java.util.Map;
//
///**
// * This class implements {@link VmLoadBalancer} with a Round Robin policy.
// * 
// * @author Bhathiya Wickremasinghe
// *
// */
//public class RoundRobinVmLoadBalancer extends VmLoadBalancer {
//	
//	private Map<Integer, VirtualMachineState> vmStatesList;
//	private int currVm = -1;
//
//	public RoundRobinVmLoadBalancer(Map<Integer, VirtualMachineState> vmStatesList){
//		super();
//		
//		this.vmStatesList = vmStatesList;
//	}
//
//	/* (non-Javadoc)
//	 * @see cloudsim.ext.VMLoadBalancer#getVM()
//	 */
//	public int getNextAvailableVm(){
//		currVm++;
//		
//		if (currVm >= vmStatesList.size()){
//			currVm = 0;
//		}
//		
//		allocatedVm(currVm);
//		
//		return currVm;
//		
//	}
//}


//Author: Murtaza Nipplewala

/**
 * Description: This algorithm selects the VM with the highest available resource 
 * capacity (e.g., remaining CPU or memory) to handle tasks, prioritizing VMs that 
 * can accommodate larger or more demanding tasks.
 * Effective in heterogeneous environments where VMs have different resource capacities.
 */

package cloudsim.ext.datacenter;

import java.util.Map;

public class RoundRobinVmLoadBalancer extends VmLoadBalancer { //capacity
    private Map<Integer, VirtualMachineState> vmStatesList;
    private Map<Integer, Integer> vmMemoryCapacities; 

    public RoundRobinVmLoadBalancer(Map<Integer, VirtualMachineState> vmStatesList,
                                       Map<Integer, Integer> vmMemoryCapacities) {
        super();
        this.vmStatesList = vmStatesList;
        this.vmMemoryCapacities = vmMemoryCapacities; 
    }

    @Override
    public int getNextAvailableVm() {
        int selectedVmId = -1;
        int maxCapacity = -1;

        for (Map.Entry<Integer, VirtualMachineState> entry : vmStatesList.entrySet()) {
            int vmId = entry.getKey();
            VirtualMachineState state = entry.getValue();

            if (state == VirtualMachineState.AVAILABLE) {
                int capacity = vmMemoryCapacities.getOrDefault(vmId, 0);
                if (capacity > maxCapacity) {
                    maxCapacity = capacity;
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




