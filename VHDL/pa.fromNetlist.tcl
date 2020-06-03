
# PlanAhead Launch Script for Post-Synthesis floorplanning, created by Project Navigator

create_project -name SpaceInvaders -dir "C:/Users/tiago/OneDrive/Documentos/Universidade/LIC/SpaceInvader/VHDL/planAhead_run_1" -part xc7a100tcsg324-3
set_property design_mode GateLvl [get_property srcset [current_run -impl]]
set_property edif_top_file "C:/Users/tiago/OneDrive/Documentos/Universidade/LIC/SpaceInvader/VHDL/SerialReceiver.ngc" [ get_property srcset [ current_run ] ]
add_files -norecurse { {C:/Users/tiago/OneDrive/Documentos/Universidade/LIC/SpaceInvader/VHDL} }
set_property target_constrs_file "uLICX_v17.1.ucf" [current_fileset -constrset]
add_files [list {uLICX_v17.1.ucf}] -fileset [get_property constrset [current_run]]
link_design
