`ifndef MY_ENV__SV
`define MY_ENV__SV
class my_env extends uvm_env;
`uvm_component_utils(my_env)
my_agent i_agt
my_agent o_agt
my_model mdl
my_scoreboard scb
uvm_tlm_analysis_fifo  #(my_transaction) agt_scb_fifo
uvm_tlm_analysis_fifo  #(my_transaction) agt_mdl_fifo
uvm_tlm_analysis_fifo  #(my_transaction) mdl_scb_fifo
function new (string name = "my_env" , uvm_component parent = env);
	super.new(name, parent);
endfunction

virtual function void build_phase (uvm_phase phase);
	super.build_phase(phase)
		i_agt = my_agent::type_id::create("i_agt", this);
		i_agt.is_active = UVM_ACTIVE
		o_agt = my_agent::type_id::create("o_agt", this);
		o_agt.is_active = UVM_PASSIVE
		mdl = my_model::type_id::create("mdl", this);
		scb = my_scoreboard::type_id::create("scb", this);
		agt_scb_fifonew("agt_scb_fifo", this);
		agt_mdl_fifonew("agt_mdl_fifo", this);
		mdl_scb_fifonew("mdl_scb_fifo", this);
endfunction

virtual function void connect_phase (uvm_phase phase);
	super.connect_phase(phase)
	i_agt.ap.connect(agt_mdl_fifo.analysis_export);
	mdl.port.connect(agt_mdl_fifo.blocking_get_export);
	endfunction

endclass
`endif