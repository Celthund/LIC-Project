----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:22:49 06/10/2020 
-- Design Name: 
-- Module Name:    LCDDispatcher - Structural 
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

entity LCDDispatcher is
	Port ( 
	        CLK : in STD_LOGIC;
			  RST : in STD_LOGIC;
			  Dval : in STD_LOGIC;
           Din : in  std_logic_vector(4 downto 0);
           Dout : out std_logic_vector(4 downto 0);
			  WRL : out STD_LOGIC;
           done : out STD_LOGIC);
end LCDDispatcher;

architecture Structural of LCDDispatcher is
type STATE_TYPE is (STATE_IDLE, STATE_WRL, STATE_DONE);
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
			when STATE_IDLE => if (Dval = '1') then NS <= STATE_WRL; else NS <= STATE_IDLE; end if;
			when STATE_WRL => NS <= STATE_DONE;
			when STATE_DONE => if (Dval = '0') then NS <= STATE_IDLE; else NS <= STATE_DONE; end if;
		end case;
	end process;
	WRK <= '1' when (CS = STATE_WRL) else '0';
	done <= '1' when (CS = STATE_DONE) else '0';
	
	Dout <= Din;
end Structural;

