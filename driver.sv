`ifndef MY_DRV__SV
`define MY_DRV__SV
class my_drv extends uvm_driver#(my_trans);
`uvm_component_utils(my_drv)

virtual my_iv vif ;


`uvm_object_utils_begin(my_drv)
`uvm_object_utils_end

//Change parent if needed !!!
function new (string name = "my_drv" , uvm_component parent = null);
	super.new(name, parent);
endfunction



virtual function void build_phase (uvm_phase phase);
	super.build_phase(phase)
	if(!uvm_config_db#(virtual my_iv)::get(this, "","vif",vif))
		`uvm_fatal("my_drv", "virtual interface must be set for my_drv!!!)"
//Write your config code here
	 if(!uvm_config_db#()::get(this, "", "", ))
		`uvm_fatal("", ""
endfunction



virtual task run_phase (uvm_phase phase);

//ADD RESET LOGIC HERE

	while(1) begin
		seq_item_port.get_next_item(req);
		drive_my_pkt(req);
		//ADD Your OWN CODE Here
		seq_item_port.item_done();
end
endtask


virtual task drive_my_pkt(my_transtr)
`uvm_info("my_drv", "Begin to drive", UVM_LOW);
//ADD YOUR OWN MAGIC DRIVE POWER HERE



`uvm_info("my_drv", "End to drive", UVM_LOW)
endtask



endclass
`endif
