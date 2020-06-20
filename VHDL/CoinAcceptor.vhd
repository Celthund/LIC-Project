----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    17:38:25 06/20/2020 
-- Design Name: 
-- Module Name:    CoinAcceptor - Behavioral 
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

entity CoinAcceptor is
    Port ( 
	        CLK : in STD_LOGIC;
			  RST : in STD_LOGIC;
			  accept : in  STD_LOGIC;
           cointIn : in  STD_LOGIC;
           coinOut : out  STD_LOGIC);
end CoinAcceptor;

architecture Behavioral of CoinAcceptor is
type STATE_TYPE is (STATE_IDLE, STATE_COIN_IN, STATE_ACCEPT);
	SIGNAL CS, NS : STATE_TYPE;
begin
	State_Transitions : process(CLK, RST)
	begin
		if (RST='1') then
			CS <= STATE_IDLE;
		elsif (rising_edge(CLK)) then
			CS <= NS;
		end if;
	end process;
	
	Next_State_Evaluation : process(CS,  cointIn, accept)
	begin
		case (CS) is 
			when STATE_IDLE => if (cointIn = '1') then NS <= STATE_COIN_IN; else NS <= STATE_IDLE; end if;
			when STATE_COIN_IN => if (accept = '1') then NS <= STATE_ACCEPT; else NS <= STATE_COIN_IN; end if;
			when STATE_ACCEPT => if (accept = '0') then NS <= STATE_IDLE; else NS <= STATE_ACCEPT; end if;
		end case;
	end process;
	coinOut <= '1' when (CS = STATE_COIN_IN) else '0';
end Behavioral;

