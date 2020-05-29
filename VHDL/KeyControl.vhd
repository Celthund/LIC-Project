----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    16:24:50 05/16/2020 
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
			RST: in STD_LOGIC;
			CLK : in  STD_LOGIC;
			KPress : in  STD_LOGIC;
			KAck : in  STD_LOGIC;
			KVal: out  STD_LOGIC;
			KScan : out  STD_LOGIC_VECTOR (1 downto 0)
	 );
end KeyControl;

architecture Behavioral of KeyControl is
	type STATE_TYPE is (STATE_IDLE, STATE_KEY_PRESSED, STATE_KEY_ACK);
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
	
	Next_State_Evaluation : process(CS, KPress , KAck)
	begin
		case (CS) is 
			when STATE_IDLE => if (KPress = '1') then NS <= STATE_KEY_PRESSED; else NS <= STATE_IDLE; end if;
			when STATE_KEY_PRESSED => if (KAck = '1') then NS <= STATE_KEY_ACK; else NS <= STATE_KEY_PRESSED; end if;
			when STATE_KEY_ACK	=> if (KAck = '1') then NS <= STATE_KEY_ACK; elsif (KPress = '1') then NS <= STATE_KEY_ACK; else NS <= STATE_IDLE; end if;
		end case;
	end process;
	KScan(0) <= '1' when (CS = STATE_KEY_PRESSED) else '0';
	KScan(1) <= '1' when (CS = STATE_IDLE) else '0';
	KVal <= '1' when (CS = STATE_KEY_PRESSED) else '0';
end Behavioral;

