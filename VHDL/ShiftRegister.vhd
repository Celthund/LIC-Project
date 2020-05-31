----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:54:28 05/20/2020 
-- Design Name: 
-- Module Name:    ShiftRegister - Behavioral 
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

entity ShiftRegister is
	 Generic (
			WIDTH : POSITIVE := 2
		);
    Port ( CLK : in  STD_LOGIC;
			  RST : in  STD_LOGIC;
           Data : in  STD_LOGIC;
           enableShift : in  STD_LOGIC;
           Q : out STD_LOGIC_VECTOR (WIDTH - 1 downto 0));
end ShiftRegister;

architecture Behavioral of ShiftRegister is
	SIGNAL IQ : STD_LOGIC_VECTOR (WIDTH - 1 downto 0);
begin
	Q <= IQ;
	process(CLK, RST, enableShift)
	begin
		if (RST='1') then
			IQ <= (others=>'0');
		elsif (enableShift = '1' and rising_edge(CLK)) then
			IQ <= Data & IQ(WIDTH - 1 downto 1); 
		end if;
	end process;	
	
end Behavioral;

