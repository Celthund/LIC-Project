--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   18:57:25 05/31/2020
-- Design Name:   
-- Module Name:   C:/Users/tiago/OneDrive/Documentos/Universidade/LIC/SpaceInvader/VHDL/KB_TEST.vhd
-- Project Name:  SpaceInvaders
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: KeyBuffer
-- 
-- Dependencies:
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
--
-- Notes: 
-- This testbench has been automatically generated using types std_logic and
-- std_logic_vector for the ports of the unit under test.  Xilinx recommends
-- that these types always be used for the top-level I/O of a design in order
-- to guarantee that the testbench will bind correctly to the post-implementation 
-- simulation model.
--------------------------------------------------------------------------------
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
 
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--USE ieee.numeric_std.ALL;
 
ENTITY KB_TEST IS
END KB_TEST;
 
ARCHITECTURE behavior OF KB_TEST IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT KeyBuffer
    PORT(
         CLK : IN  std_logic;
         RST : IN  std_logic;
         DAV : IN  std_logic;
         ACK : IN  std_logic;
         DAC : OUT  std_logic;
         DVal : OUT  std_logic;
         D : IN  std_logic_vector(3 downto 0);
         Q : OUT  std_logic_vector(3 downto 0)
        );
    END COMPONENT;
    

   --Inputs
   signal CLK : std_logic := '0';
   signal RST : std_logic := '0';
   signal DAV : std_logic := '0';
   signal ACK : std_logic := '0';
   signal D : std_logic_vector(3 downto 0) := (others => '0');

 	--Outputs
   signal DAC : std_logic;
   signal DVal : std_logic;
   signal Q : std_logic_vector(3 downto 0);

   -- Clock period definitions
   constant CLK_period : time := 10 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: KeyBuffer PORT MAP (
          CLK => CLK,
          RST => RST,
          DAV => DAV,
          ACK => ACK,
          DAC => DAC,
          DVal => DVal,
          D => D,
          Q => Q
        );

   -- Clock process definitions
   CLK_process :process
   begin
		CLK <= '0';
		wait for CLK_period/2;
		CLK <= '1';
		wait for CLK_period/2;
   end process;
 

   -- Stimulus process
   stim_proc: process
   begin		
      -- hold reset state for 100 ns.
      wait for 100 ns;	

      wait for CLK_period*10;

      -- insert stimulus here 

      wait;
   end process;

END;
