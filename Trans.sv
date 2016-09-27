`ifndef MY_TRANS__SV
`define MY_TRANS__SV

class my_trans extends uvm_sequence_item;
function new (string name = " my_trans");
	super.new();
endfunction
	rand bit[24:0]	dmac;
	rand byte	smac;
	int	dog;
constraint p_cons{
//Add constraints here
}
function void calc ()
//ADD CODE HERE FOR FUNCTION
endfunction
`uvm_object_utils_begin(my_trans)
	//`uvm_field_int(name, UVM_ALL_ON)
`uvm_object_utils_end

endclass

`endif
