class case0_sequence extends uvm_sequence#(my_transaction);

my_transaction m_trans

function new (string name = "case0_sequence");
	super.new(name);
endfunction

virtual task body();
	if(starting_phase != null)
		starting_phase.raise_objection(this);
		//ADD YOUR CODE HERE
		`uvm_do(m_trans)
	if(starting_phase != null)
		starting_phase.drop_objection(this);
endtask

`uvm_object_utils(case0_sequence)
endclass