----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:47:51 05/13/2020 
-- Design Name: 
-- Module Name:    Register_D - Behavioral 
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

entity Register_D is
	Generic (
		WIDTH : POSITIVE := 1
	);
	Port ( 
	   CLK : in  STD_LOGIC;
		RST : in STD_LOGIC;
	   D : in  STD_LOGIC_VECTOR(WIDTH-1 downto 0);
		Q : out  STD_LOGIC_VECTOR(WIDTH-1 downto 0)
	);
end Register_D;

architecture Behavioral of Register_D is

begin
	process(CLK, RST)
	begin
		if (RST='1') then
			Q <= (others=>'0');
		elsif (rising_edge(CLK)) then
			Q <= D;
		end if;
	end process;	
end Behavioral;

