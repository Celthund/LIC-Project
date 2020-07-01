----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:08:10 05/13/2020 
-- Design Name: 
-- Module Name:    Decoder - Behavioral 
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

entity Decoder is
    Port ( S0 : in  STD_LOGIC;
			  S1 : in  STD_LOGIC;
           O : out STD_LOGIC_VECTOR (2 downto 0));
end Decoder;

architecture Behavioral of Decoder is

begin
		O <= "000" when S0 = '1' and S1 = '1' else
			  "001" when S0 = '0' and S1 = '0' else
			  "010" when S0 = '1' and S1 = '0' else
			  "100" when S0 = '0' and S1 = '1';
end Behavioral;

	