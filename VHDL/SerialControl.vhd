----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    20:44:26 05/27/2020 
-- Design Name: 
-- Module Name:    SerialControl - Structural 
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

entity SerialControl is
    Port ( 
	        CLK : in STD_LOGIC;
			  RST : in STD_LOGIC;
			  enRx : in  STD_LOGIC;
           accept : in  STD_LOGIC;
           pFlag : in  STD_LOGIC;
           dFlag : in  STD_LOGIC;
           RXerror : in  STD_LOGIC;
			  wr : out STD_LOGIC;
           init : out  STD_LOGIC;
           DXval : out  STD_LOGIC);
end SerialControl;

architecture Structural of SerialControl is
type STATE_TYPE is (STATE_IDLE, STATE_KEY_ENRX, STATE_DFLAG_ON, STATE_NO_ERROR, STATE_ACCEPT_ON);
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
	
	Next_State_Evaluation : process(CS, enRx , dFlag, pFlag, accept, RXerror)
	begin
		case (CS) is 
			when STATE_IDLE => if (enRx = '1') then NS <= STATE_IDLE; else NS <= STATE_KEY_ENRX; end if;
			when STATE_KEY_ENRX => if (enRx = '1') then NS <= STATE_IDLE; elsif (enRx = '0' and dFlag = '1') then NS <= STATE_DFLAG_ON; else NS <= STATE_KEY_ENRX; end if;
			when STATE_DFLAG_ON	=> if (enRx = '1') then NS <= STATE_IDLE; elsif (enRx = '0' and pFlag = '0')  then NS <= STATE_DFLAG_ON; elsif (enRx = '0' and pFlag = '1' and RXerror = '1') then NS <= STATE_IDLE; else NS <= STATE_NO_ERROR; end if;
			when STATE_NO_ERROR => if (accept = '0') then NS <= STATE_NO_ERROR; else NS <= STATE_ACCEPT_ON; end if;
			when STATE_ACCEPT_ON => if (accept = '1') then NS <= STATE_ACCEPT_ON; else NS <= STATE_IDLE; end if;
		end case;
	end process;
	init <= '1' when (CS = STATE_IDLE) else '0';
	wr <= '1' when (CS = STATE_KEY_ENRX) else '0';
	DXval <= '1' when (CS = STATE_NO_ERROR) else '0';
end Structural;

