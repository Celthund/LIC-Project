----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    22:35:57 05/13/2020 
-- Design Name: 
-- Module Name:    KeyControl - Behavioral 
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

entity KeyControl is
	Port ( 
		CLK : in  STD_LOGIC;
      RST : in  STD_LOGIC;
      KAck : in  STD_LOGIC;
      KVal : in  STD_LOGIC;
	   NOutIn : out  STD_LOGIC;
		Detect : out  STD_LOGIC);
end KeyControl;

architecture Behavioral of KeyControl is
	type STATE_TYPE is (STATE_IDLE, STATEIN0, STATEIN1, STATEIN2, STATEIN3,
											  STATE_OUT0,	STATE_OUT1, STATE_OUT2, STATE_OUT3);
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
	
	Next_State_Evaluation : process(CLK, RST)
	begin
		case (CS) is 
			when STATE_IDLE	=> if (S0 = '1') then NS <= STATEIN0; elsif (S1 = '1') then NS <= STATE_OUT0; else NS <= STATE_IDLE; end if;
			when STATEIN0	=> if (S0 = '0') then NS <= STATE_IDLE; elsif (S1 = '1') then NS <= STATEIN1; else NS <= STATEIN0; end if;
			when STATEIN1	=> if (S1 = '0') then NS <= STATEIN0; elsif (S0 = '0') then NS <= STATEIN2; else NS <= STATEIN1; end if;
			when STATEIN2	=> if (S1 = '1') then NS <= STATEIN2; elsif (S0 = '1') then NS <= STATEIN3; else NS <= STATEIN1; end if;
			when STATEIN3	=> NS <= STATE_IDLE;
			
			when STATE_OUT0	=> if (S1 = '0') then NS <= STATE_IDLE; elsif (S0 = '1') then NS <= STATE_OUT1; else NS <= STATE_OUT0; end if;
			when STATE_OUT1	=> if (S0 = '0') then NS <= STATE_OUT0; elsif (S1 = '0') then NS <= STATE_OUT2; else NS <= STATE_OUT1; end if;
			when STATE_OUT2	=> if (S0 = '1') then NS <= STATE_OUT2; elsif (S0 = '0') then NS <= STATE_OUT3; else NS <= STATE_OUT1; end if;
			when STATE_OUT3	=> NS <= STATE_IDLE;
		end case;
	end process;
	detect <= '1' when (CS = STATEIN3 or CS = STATE_OUT3) else '0';
	NOutIn <= '1'  when (CS = STATEIN3) else '0';

end Behavioral;

