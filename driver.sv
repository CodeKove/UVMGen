`ifndef MY_DRIVER__SV
`define MY_DRIVER__SV
class my_driver extends uvm_driver#(sdas);

virtual dasd sdasd ;

`uvm_component_utils(my_driver)



function new (string name = "my_driver" , uvm_component parent = null);
	super.new(name, parent);
endfunction



virtual function void build_phase (uvm_phase phase);
	super.build_phase(phase)
	if(!uvm_config_db#(virtual dasd)::get(this, "","sdasd",sdasd))
		`uvm_fatal("my_driver", "virtual interface must be set formy_driver!!!)"
endfunction



virtual task run_phase (uvm_phase phase)

//ADD RESET LOGIC HERE

	while(1) begin
		seq_item_port.get_next_item(req);
		drive_my_pkt(req);
		//ADD Your OWN CODE Here
		seq_item_port.item_done();
end
endtask


virtual task drive_my_pkt(sdastr)
`uvm_info("my_driver", "Begin to drive", UVM_LOW);
//ADD YOUR OWN MAGIC DRIVE POWER HERE



`uvm_info("my_driver", "End to drive", UVM_LOW)
endtask



endclass
`endif
