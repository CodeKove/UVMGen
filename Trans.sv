`ifndef TRANS__SV
`define TRANS__SV


class trans extends uvm_sequence_item;
function new (string name = " trans");
	super.new();
endfunction
	rand bit	smac;
	rand int	hdd;

	int	ds;

constraintcons1{
//Add constraints here
}

function ysdhd func1 ()
//ADD CODE HERE FOR FUNCTION
endfunction

`uvm_object_utils_begin(trans)
	`uvm_field_int(name, UVM_ALL_ON)
`uvm_object_utils_end

endclass

`endif
