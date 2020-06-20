----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:14:37 05/20/2020 
-- Design Name: 
-- Module Name:    KeyBufferControl - Behavioral 
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

entity KeyBufferControl is
    Port ( 
			CLK : in  STD_LOGIC;
		   RST : in  STD_LOGIC;
			DAV : in  STD_LOGIC;
         ACK : in  STD_LOGIC;
         DAC : out  STD_LOGIC;
         DVal : out  STD_LOGIC;
         WReg : out  STD_LOGIC
		);
end KeyBufferControl;

architecture Behavioral of KeyBufferControl is
	type STATE_TYPE is (STATE_IDLE, STATE_DAV_ON, STATE_DAV_OFF, STATE_ACK_ON);
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
	
	Next_State_Evaluation : process(CS, DAV, ACK)
	begin
		case (CS) is 
			when STATE_IDLE => if (DAV = '1') then NS <= STATE_DAV_ON; else NS <= STATE_IDLE; end if;
			when STATE_DAV_ON => if (DAV = '0') then NS <= STATE_DAV_OFF; else NS <= STATE_DAV_ON; end if;
			when STATE_DAV_OFF	=> if (ACK = '1') then NS <= STATE_ACK_ON; else NS <= STATE_DAV_OFF; end if;
			when STATE_ACK_ON	=> if (ACK = '0') then NS <= STATE_IDLE; else NS <= STATE_ACK_ON; end if;
		end case;
	end process;
	WReg <= '1' when (CS = STATE_DAV_ON) else '0';
	DAC <= '1' when (CS = STATE_DAV_ON) else '0';
	DVAL <= '1' when (CS = STATE_DAV_OFF) else '0';
end Behavioral;



