----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    21:50:55 05/18/2020 
-- Design Name: 
-- Module Name:    KeyboardReaderWrapper - Behavioral 
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

entity KeyboardReaderWrapper is
    Port (
         CLK 	: IN  std_logic;
			RESET : IN std_logic;
         KLINS : IN  std_logic_vector(3 downto 0);
         ACK 	: IN  std_logic;
         KCOLS : OUT  std_logic_vector(2 downto 0);
         D 		: OUT  std_logic_vector(3 downto 0);
         DVAL 	: OUT  std_logic
        );
end KeyboardReaderWrapper;

architecture Behavioral of KeyboardReaderWrapper is

	COMPONENT KeyDecoder
	PORT(
		RST: in STD_LOGIC;
		CLK : in  STD_LOGIC;
		KAck : in  STD_LOGIC;
		KVal: out STD_LOGIC;
		K : out  STD_LOGIC_VECTOR (3 downto 0);
		PENC_IN: in  STD_LOGIC_VECTOR (3 downto 0);
		DEC_OUT : out STD_LOGIC_VECTOR (2 downto 0)
		);
	END COMPONENT;
	
begin
	Inst_key_decode: KeyDecoder 
	PORT MAP(
		RST => RESET,
		CLK  => CLK,
		KAck => ACK,
		KVal => DVAL,
		K => D,
		PENC_IN => KLINS,
		DEC_OUT => KCOLS
	);

end Behavioral;
