----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    18:52:32 05/13/2020 
-- Design Name: 
-- Module Name:    PriorityEnconder - Behavioral 
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

entity PriorityEnconder is
    Port ( I0 : in  STD_LOGIC;
           I1 : in  STD_LOGIC;
           I2 : in  STD_LOGIC;
           I3 : in  STD_LOGIC;
           Y : out  STD_LOGIC_VECTOR (1 downto 0);
           GS : out  STD_LOGIC);
end PriorityEnconder;

architecture Behavioral of PriorityEnconder is

begin
--	I3	I2	I1	I0	O1	O0	V
--	0	0	0	0	x	x	0
--	0	0	0	1	0	0	1
--	0	0	1	x	0	1	1
--	0	1	x	x	1	0	1
--	1	x	x	x	1	1	1
	Y <= "11" when I3='1' else
		  "10" when I2='1' else
		  "01" when I1='1' else
		  "00" when I0='1' else
		  "00";
	GS <= I3 or I2 or I1 or I0;
end Behavioral;

