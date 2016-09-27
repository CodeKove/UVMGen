`ifndef MY_SCB__SV
`define MY_SCB__SV

class my_scb extends uvm_scoreboard;
	`uvm_component_utils(my_scb)
	uvm_blocking_get_port #(my_transaction) exp_port;
	uvm_blocking_get_port #(my_transaction) act_port;

function new(string name, uvm_component parent);
	super.new(name, parent);
endfunction

virtual function void build_phase(uvm_phase phase);
	super.build_phase(phase);
	exp_port= new("exp_port", this);
	act_port= new("act_port", this);
endfunction

virtual task run_phase(uvm_phase phase);
	super.run_phase(phase);
	fork
//ADD Your code here
	join
endtask

endclass
`endif
