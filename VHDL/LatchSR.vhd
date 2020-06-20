----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    21:00:38 06/17/2020 
-- Design Name: 
-- Module Name:    LatchSR - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity LatchSR is
	Port ( 
	   CLK : in  STD_LOGIC;
	   RST : in  STD_LOGIC;
	   SET : in  STD_LOGIC;
	   Q : out  STD_LOGIC
	);
end LatchSR;

architecture Behavioral of LatchSR is

begin
	process(CLK, RST, SET)
	begin
		if (rising_edge(CLK)) then
			if (RST='1') then
				Q <= '0';
			elsif (SET='1') then
				Q <= '1';
			end if;
		end if;
	end process;	
end Behavioral;

