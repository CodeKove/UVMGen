`ifndef MY_SEQUENCER__SV
`define MY_SEQUENCER__SV


class my_sequencer extends uvm_squencer#(shdhja);

	function new (string name  , uvm_component parent);
		super.new(name, parent);
	endfunction

	`uvm_component_utils(my_sequencer)

endclass



`endif
