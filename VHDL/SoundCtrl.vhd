----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    15:21:26 06/14/2020 
-- Design Name: 
-- Module Name:    SoundCtrl - Behavioral 
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

entity SoundCtrl is
	Port ( 
	        CLK : in STD_LOGIC;
			  RST : in STD_LOGIC;
			  Dval : in STD_LOGIC; 
			  Enable : out STD_LOGIC;
			  done : out STD_LOGIC);
end SoundCtrl;

architecture Behavioral of SoundCtrl is
type STATE_TYPE is (STATE_IDLE, STATE_ENABLE, STATE_DONE);
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
	
	Next_State_Evaluation : process(CS,  Dval)
	begin
		case (CS) is 
			when STATE_IDLE => if (Dval = '1') then NS <= STATE_ENABLE; else NS <= STATE_IDLE; end if;
			when STATE_ENABLE => NS <= STATE_DONE;
			when STATE_DONE => if (Dval = '0') then NS <= STATE_IDLE; else NS <= STATE_DONE; end if;
		end case;
	end process;
	Enable <= '1' when (CS = STATE_ENABLE) else '0';
	done <= '1' when (CS = STATE_DONE) else '0';
end Behavioral;

