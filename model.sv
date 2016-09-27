`ifndef MY_MODEL__SV
`define MY_MODEL__SV
class my_model extends uvm_scoreboard;
	`uvm_component_utils(my_model)
	a #(b) c;

function new (string name , uvm_component parent);
	super.new(name, parent);
endfunction

virtual function void build_phase(uvm_phase phase);
	super.build_phase(phase);
	c= new("c", this);
endfunction

virtual task run_phase(uvm_phase phase);
	super.run_phase(phase);
	fork
//ADD Your code here
	join
endtask
endclass
`endif
