----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    15:28:05 06/14/2020 
-- Design Name: 
-- Module Name:    SoundCMDDecoder - Behavioral 
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

entity SoundCMDDecoder is
    Port ( S0 : in  STD_LOGIC;
			  S1 : in  STD_LOGIC;
			  Enable : in STD_LOGIC;
			  Stop: out STD_LOGIC;
           Play: out STD_LOGIC;
			  setSound: out STD_LOGIC;
			  setVolume: out STD_LOGIC);
end SoundCMDDecoder;

architecture Behavioral of SoundCMDDecoder is

begin
		Stop <= '1' when S0 = '0' and S1 = '0' and Enable = '1' else '0';
		Play <= '1' when S0 = '1' and S1 = '0' and Enable = '1' else '0';
		setSound <= '1' when S0 = '0' and S1 = '1' and Enable = '1' else '0';
		setVolume <= '1' when S0 = '1' and S1 = '1' and Enable = '1' else '0';
end Behavioral;

