`ifndef MY_SEQUENCER__SV
`define MY_SEQUENCER__SV


class my_sequencer extends uvm_sequencer#(my_trans);
	`uvm_component_utils(my_sequencer)

	function new (string name, uvm_component parent);
		super.new(name, parent);
	endfunction


endclass

`endif
