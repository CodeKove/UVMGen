`ifndef MY_AGENT__SV
`define MY_AGENT__SV
class my_agent extends uvm_agent#;
	`uvm_component_utils(my_agent)

	my_driver drv;
	my_monitormon;
	my_sequencersqr;
	uvm_analysis_port #(my_transaction) ap;

function new (string name  , uvm_component parent);
	super.new(name, parent);
endfunction

virtual function void build_phase (uvm_phase phase);
	super.build_phase(phase)
	if (is_active == UVM_ACTIVE) begin
		drv = my_driver::type_id::create("drv", this);
		sqr = my_sequencer::type_id::create("sqr", this);
	end
mon = my_monitor::type_id::create("mon", this);
endfunction

virtual function void connect_phase (uvm_phase phase);
	super.connect_phase(phase)
	if (is_active == UVM_ACTIVE) begin
drv.seq_item_port.connect(sqr.seq_item_export);
	end
	ap = mon.ap
endfunction

endclass
`endif
