`ifndef MY_MONITOR__SV
`define MY_MONITOR__SV
class my_monitor extends uvm_monitor;
`uvm_component_utils(my_monitor)
	virtual my_if vif ;
	uvm_analysis_port #(my_trans) ap;
covergroup;
	coverpoint;
endgroup;


function new (string name = "my_monitor" , uvm_component parent = null);
	super.new(name, parent);
endfunction


virtual function void build_phase (uvm_phase phase);
	super.build_phase(phase)
	if(!uvm_config_db#(virtual my_if)::get(this, "","vif",vif))
		`uvm_fatal("my_monitor", "virtual interface must be set for my_monitor!!!)"
	ap = new("ap" , this)
endfunction


virtual task run_phase (uvm_phase phase)

//ADD Your OWN CODE Here
my_trans tr;

fork
while(1) begin
	tr = new("tr");
	collect_my_pkt(tr);
end
join

endtask



virtual task collect_my_pkt(my_trans tr)
`uvm_info("my_monitor", "Begin to collect", UVM_LOW);
//ADD YOUR OWN COLLECT CODE HERE

`uvm_info("my_monitor", "End to collect", UVM_LOW)
endtask


endclass
`endif
